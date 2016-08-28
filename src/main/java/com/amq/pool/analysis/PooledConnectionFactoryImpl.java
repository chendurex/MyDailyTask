package com.amq.pool.analysis;

/**
 * PooledConnectionFactory 一个管理PooledConnectionFactory并创建connection的工厂
 * 1，构建一个PooledConnectionFactory时，必须设置setConnectionFactory，因为它仅仅是代理而已，真正产生connection是ConnectionFactory
 *
 * 2，PooledConnectionFactory 仅仅负责创建connection，后续的维护操作都是交由connection管理
 *
 * 3，常规创建session的方式为：PooledConnectionFactory.createConnection，关闭则直接调用close即可
 *
 * 4，参数解释(这些参数全部都是为connection服务的，即每个connection的创建都是复用同样的参数，从而减轻在connection设置参数)：
 *
 * 4.1，maximumActiveSessionPerConnection：默认值500，表示所有的正在运行的session数量
 *      如果超过这个数量，再创建session默认是进行阻塞(根据超过session数量策略具体分析)，
 *      如果使用线程池情况下，最好保持线程池(并根据消费能力考虑阻塞队列大小)最大数量跟session数量一致
 *
 * 4.2，maxConnections：每个PooledConnectionFactory最多可以创建的连接数，如果没有超过最大连接数，那么每次createConnection都是
 *      创建一个新的connection放入缓存池，知道达到最大值才会复用连接。
 *      注意：这里表面针对的是每个factory最多创建两个max个连接，但是实际是针对每个连接的key(ConnectionKey)。
 *      即相同的key最多有max个connection。如果我们创建连接时使用了不同的key(不同的username+password)那么，
 *      真实最多可以产生connection=key*maxConnection个连接(这里应该是amq的bug)
 *      同样的道理，PooledConnectionFactory.setMaximumActiveSessionPerConnection参数，
 *      也是针对同一个key(key=transaction+ackMode)那么真实最多可以产生的maxSession=key*maxActiveSession
 *
 * 4.3，idleTimeout：连接的空闲时间，默认是三十秒，因为创建连接非常消耗资源，所以尽量把时间设置的长一些
 *      如果使用了连接池，那么调用了connection.close方法，连接不是真正的关闭，而是放入到一个池，直到idleTimeOut时间到了才关闭
 *      如果在idleTimeOut期间又创建了一个connection，并且当前连接数量达到maxConnection，那么会从池中复用一个连接，并重置idleTimeOut
 *      如果未调用close方法，则连接一直处于活跃状态
 *
 * 4.4，expiryTimeout：连接超时，0表示不超时
 *      如果某个连接设置了超时时间>0，那么以创建时间为标准，显式调用了close方法后，达到expiryTimeout时间后，连接会被回收。
 *      请注意，这里跟idleTimeout区别是：就算是重新获取了连接，idleTimeout是会重置初始值的，但是expiryTimeout是不会重置的
 *      如：expiryTimeout=100ms，idleTimeout=80ms，第一次close后，过70ms后再次获取连接，idleTimeout超时时间还是为80ms，
 *      再次close后，过50ms获取的连接是新的连接，而不是以idleTimeout=80ms计算，而是以expiryTimeout<80+50=130ms计算
 *      所以这个设置最好是默认值0不要动，以idleTimeout为超时时间
 *
 * 4.5，timeBetweenExpirationCheckMillis：默认-1，每次检查过期连接的间隔时间，-1表示一直持续检查，
 *      如果设置了>0则有可能出现过期时间的误差
 * 4.6，createConnectionOnStartup：默认true，显式调用PooledConnectionFactory.start()方法则默认创建一个连接
 *
 * 4.7，useAnonymousProducers：默认true，表示使用匿名的目的地，即session.createProducer(null)创建默认生产者,这里主要是提供给
 *      一些没有目的地的producer，保证每次查看到的生产者都是同一个而不是随机产生的
 *
 * 4.8，blockIfSessionPoolIsFull：默认true，如果session达到maximumActiveSessionPerConnection设置则再次获取的session会阻塞
 *
 * 4.9，blockIfSessionPoolIsFullTimeout：默认-1，不超时。根据blockIfSessionPoolIsFull设置超时时间，
 *      如果>0则到达超时时间则不在继续等待而抛出未获取session异常
 *
 * 4.10，reconnectOnException：默认true，如果抛出异常是否继续重连，这里主要作用到集群情况，如failover:(tcp://localhost:61616)
 */
public interface PooledConnectionFactoryImpl {
}
