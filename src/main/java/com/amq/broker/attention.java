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
     */
}
