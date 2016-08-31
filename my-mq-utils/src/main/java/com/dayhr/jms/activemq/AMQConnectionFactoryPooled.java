
package com.dayhr.jms.activemq;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:04
 */
public class AMQConnectionFactoryPooled {
    private int maxConnections;
    private int maximumActiveSessionPerConnection;
    private long idleTimeout;
    private long expiryTimeout;
    private long timeBetweenExpirationCheckMillis;
    private boolean createConnectionOnStartup;
    private boolean useAnonymousProducers;
    private boolean blockIfSessionPoolIsFull;
    private long blockIfSessionPoolIsFullTimeout;
    private boolean reconnectOnException;


}
