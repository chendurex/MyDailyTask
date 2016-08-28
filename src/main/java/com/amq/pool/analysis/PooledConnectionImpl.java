package com.amq.pool.analysis;

/**
 * PooledConnection实现原理
 * 一个实现了TopicConnection, QueueConnection的连接，即生产connection的池
 * 1，内部获取连接是代理了ConnectionPool(请查看ConnectionPool实现)，它仅仅作为一个包装器，所有的创建session，producer、consumer
 * 等操作都是代理给ConnectionPool处理，它实现了PooledSessionEventListener接口，即保持对session、队列等的创建，关闭监听，
 *
 * 2，显式的调用PooledConnection.close()仅仅是把当前对象清理，把session监听清理了，并未关闭连接，并且调用createSession的话，
 * 还是可以再次创建session，达到连接的复用
 */
public interface PooledConnectionImpl {
}
