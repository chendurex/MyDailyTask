package com.amq.broker;

/**
 * @author chen
 * @description
 * @pachage com.amq.broker
 * @date 2016/8/25 9:59
 */
public class attention {
    /**
     * 1，PooledConnectionFactory内部维护了一个连接池，使用factory.createConnection();默认会根据设置的连接数创建连接，如果连接数未超过
     * 最大连接数则创建新的连接，如果超过了则复用旧的连接。但是这里返回的不是一个普通的connection，而是一个包装connection的PooledConnection，里面还存储了
     * 一些session的信息，如果不显式转换为PooledConnection，那么会默认使当前连接池作为普通的连接，每次close是把连接对象关闭，
     * 并且关闭了一些PooledConnection对象一些session和关联的很多对象，开销非常的大，并没有做到连接池复用连接的效果，所以最好显式的转换为
     * PooledConnection，并且使用getConnection获取连接，这样保证每次显式的close仅仅是把连接放入连接池，并设置启用状态为false，并未关闭连接
     *
     * 2，每次使用createConnection时是创建一个连接池，连接池可以无限制的创建，但是如果设置了PooledConnectionFactory.setMaxConnections(2)
     * 那么只能存在两个连接，如果连接池超过2个，那么多个连接池使用同一个连接
     *
     * 3，但是如果设置了PooledConnectionFactory.setMaximumActiveSessionPerConnection，这个是设置所有的正在运行的session数量
     * 如果超过这个数量，再创建session默认是进行阻塞，这里使用一个线程一个session的话，最好保持线程池(这里需要考虑线程队列大小)最大数量跟session数量一致
     *
     * 4，PooledConnectionFactory.setBlockIfSessionPoolIsFull(false)，当session满了，而且设置了非阻塞，那么在此获取session时会抛异常，
     * 如果设置为阻塞，并且线程模式是同步的话，那么在同一个connection千万别连续获取多个session，可能导致前面的session未执行完毕，后面获取不了session
     * 出现线程阻塞无法释放
     *
     * 5，不管是connection、session、product、consumer、message都需要为每个对象设置一个唯一ID，用于溯源跟踪与消息重复验证
     *
     * 6，producer设置time_to_live时间太短，发送消息的时候会验证producer是否过期，如果过期则会抛出session timeOutException
     *
     * 7，prefetch：控制消费者每次存储的消息数量，消费者跟broker中间有个缓冲队列，broker每次接收到producer的消息后会立即推送给consumer
     * 的缓冲队列，一直达到consumer的prefetch上限，才会停止推送，consumer将从缓存队里获取消息并消费，如果consumer的消费能力
     * 不高，那么会导致缓冲队列阻塞非常多的消息，而且如果设置了多consumer，那么可能导致A consumer堆积非常多消息，而B consumer
     * 没有任何可消费的消息，直到A consumer 关闭或者整个connection关闭，才有可能把消息从新分发给B consumer
     * 这时可以根据自己需求设置大小，如果消费能力低则设置为1，保证每次消费一条消息，
     * broker推送一条，防止消息在consumer堆积，具体设置请查看文档：http://activemq.apache.org/what-is-the-prefetch-limit-for.html
     * 以及出现的原因：http://activemq.apache.org/i-do-not-receive-messages-in-my-second-consumer.html
     * 另外因为这个prefetch间接影响的一个地方是配置了consumer pool，因为pool会重复利用consumer，正常情况close掉consumer
     * 则会直接把未消费的消息重新派发到其它的consumer或者broker存储，但是因为pool实际未关闭consumer，导致消息一直存储到缓冲池，
     * 知道consumer重新利用才会重新消息或者分派到其它的consumer，这里变相的出现一个假象就是：消息一直阻塞到consumer不消费
     * 根据当前问题：org.apache.activemq.pool.PooledConnectionFactory 连接池是不缓存consumer
     * 建议：如果使用了多consumer那么最好prefetch设置在20个以内，消费能力低的直接设置为1
     *
     * 8，PooledConnectionFactory.setMaxConnections 这里表面针对的是每个factory最多创建两个max个连接，但是实际是针对每个连接
     * 的key。即相同的key最多有max个connection。每个connection 都有一个key(username+password，即createConnection(username,password))，
     * 如果我们创建连接时使用了不同的key那么，真实最多可以产生connection=key*maxConnection个连接(这里应该是amq的bug)
     * 同样的道理，PooledConnectionFactory.setMaximumActiveSessionPerConnection参数，也是针对同一个key(key=transaction+ackMode)
     * 那么真实最多可以产生的maxSession=key*maxActiveSession
     *
     * 9，Connection c1 = PooledConnectionFactory.createConnection();与
     * PooledConnection pooledConnection = (PooledConnection)factory.createConnection();
     * Connection c2 = pooledConnection.getConnection();区别：
     * c1其实是一个PooledConnection的实现，本身是不持有任何的连接，而是代理了ConnectionPool，真正持有连接的ConnectionPool，
     * 调用c1.close仅仅是把当前代理关闭了，而并非关闭了真正的连接，达到连接复用的效果。
     * c2才持有真实的连接，它的连接池是基于commons-pool2的连接池实现，调用c2.close则是关闭了当前连接，而间接关闭了
     * 关闭了真实的连接，并非做到连接池的效果，如果确实需要关闭真实连接则采用c2的方式，否则建议采用c1的获取连接方式
     * 注：
     *
     */
}
