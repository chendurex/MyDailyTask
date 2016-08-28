package com.amq.pool.analysis;

/**
 * ConnectionKey 和 SessionKey的作用一样的，分析ConnectionKey即可
 * 1，ConnectionPool是由commons-pools2的连接池管理，而commons-pools2管理的是键值对的连接，
 * 所以这里使用了ConnectionKey作为key，对象重写了hashcode，以username+password hash，
 * 判断一个连接池拥有多少个连接则直接获取key对应的Collection<Connection>.size()即可
 */
public interface ConnectionKeyImpl {
}
