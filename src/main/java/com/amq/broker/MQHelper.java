
package com.amq.broker;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Session;
import java.util.concurrent.CountDownLatch;


public class MQHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MQHelper.class);
    private final static PooledConnectionFactory cf = new PooledConnectionFactory();
    private static volatile Connection connection = null;
    private static boolean isTransaction = true;
    private static int ackMode = Session.AUTO_ACKNOWLEDGE;
    private static final int MAX_CONNECTION = 1;
    /**
     * 当前连接最大的session数量，超过这个数量，再次创建的session默认会被阻塞
     */
    private static final int MAX_SESSION = 100;
    /**
     * 连接的空闲时间，默认是三十秒，因为创建连接非常消耗资源，所以尽量把时间设置的长一些
     * 如果使用了连接池，那么调用了connection.close方法，连接不是真正的关闭，而是放入到一个池，直到idleTimeOut时间到了才关闭
     * 如果在idleTimeOut期间又创建了一个connection，并且设置了连接池大小为1，那么获取的是同一个连接
     */
    private static int IDLE_TIME_OUT = 1000_00;

    /**
     * 连接超时，0表示不超时
     * 如果某个连接设置了超时时间，那么以超时时间为标准，就算显示的调用close方法，也只是把连接放入连接池，
     * 如果有连接重新调用获取连接是会重新充池获取未超时的连接继续使用
     * 如果同时配置了idle_time_out那么time_out为主，idle_time_out为辅
     */
    private static long EXPIRY_TIME_OUT = 0l;

    static {
        LOGGER.info("PooledConnectionFactory initializing.");
        cf.setConnectionFactory(new ActiveMQConnectionFactory(JmsComstant.URL));
        cf.setMaxConnections(MAX_CONNECTION);
        cf.setMaximumActiveSessionPerConnection(MAX_SESSION);
        cf.setBlockIfSessionPoolIsFull(true);
        cf.setIdleTimeout(IDLE_TIME_OUT);
        cf.setExpiryTimeout(EXPIRY_TIME_OUT);
        LOGGER.info("ConnectionFactory initialized.");
    }

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (MQHelper.class) {
                if (connection == null) {
                    connection = getConnection(new IdGenerator("dayhr-client-id").generateId());
                }
            }
        }
        return connection;
    }

    private static Connection getConnection(String clientId) {
        final CountDownLatch latch = new CountDownLatch(1);
        Connection connection = null;
        try {
            PooledConnection cPool = (PooledConnection)cf.createConnection();
            connection = cPool.getConnection();
            connection.setClientID(clientId);
            connection.start();
            connection.setExceptionListener(new ExceptionListener() {
                @Override
                public void onException(JMSException e) {
                    latch.countDown();
                    LOGGER.error("create connection error, error msg : ", e);
                }
            });
            latch.countDown();
        } catch (JMSException e) {
           // ignore
        }
        return connection;
    }

    public static Session getSession() throws JMSException {
        return getSession(isTransaction, ackMode);
    }

    public static Session getSession(boolean isTransaction, int acknowledge) throws JMSException {
        return getConnection().createSession(isTransaction, acknowledge);
    }

}


