package com.amq.pool.analysis;

/**
 * ConnectionPool 实现原理
 * 一个真正持有jms connection的连接，并且管理着session，并为PooledConnection提供服务
 * 1，它由PooledConnectionFactory.initConnectionsPool()创建，即初始化连接时创建
 * 或者显式的PooledConnectionFactory.createConnection()创建一个PooledConnection，然后内部再代理创建一个ConnectionPool
 * 2，内部实现是基于commons-pools2的键值对连接池，每次创建一个ConnectionPool都会被commons-pools2管理。
 * 3，PooledConnectionFactory.createConnection()创建连接时，其实内部调用的是PooledConnectionFactory.createConnection(username,password)，
 * ConnectionPool根据username+password组合成一个ConnectionKey对象，并重写了hashcode，并作为当前连接的一个key。
 * 然后再传入到commons-pools2的连接池管理，保持着一个map(key,ConnectionPool)的键值对关系，每次获取connection的时候都是根据
 * key获取connection
 * 4，根据PooledConnectionFactory.setMaxConnections得知，每个工厂最多有max个连接，那么ConnectionPool是怎么实现的？
 * 它也是根据key作为关键字，然后调用commons-pools2去获取连接，但是前面有说过，每个key对应一个连接，所以这里进一步优化，不采用
 * map(key,ConnectionPool)而是使用map(key,Collections<ConnectionPool>)，这样获取到的就是一个集合，
 * 然后再从集合中获取一个连接便控制了连接数
 * 5，ConnectionPool没有实现jms的connection，所以它不是作为一个连接，它仅仅负责管理一个连接，而直接被PooledConnection使用，
 * 6，ConnectionPool有非常多的设置，如idleTimeout、expiryTimeout、blockIfSessionPoolIsFull等，其实大家用的时候会发现，
 * 这些属性都是PooledConnectionFactory设置的，其实内部都是交给ConnectionPool管理，再底层则是交给commons-poos2管理
 * 7，获取ConnectionPool方式：PooledConnection pooledConnection = (PooledConnection)factory.createConnection()
 *                           ConnectionPool pooledConnection = (ConnectionPool)pooledConnection.getConnection();
 * 8，显式的调用 ConnectionPool.close则会直接把最原始的jms.connection关闭，再调用createSession会抛异常，而并非是关闭ConnectionPool，
 *    防止出现不可预见异常，所以最好别使用此方法获取连接
 *
 * 9，
 */
public interface ConnectionPooledImpl {
}
