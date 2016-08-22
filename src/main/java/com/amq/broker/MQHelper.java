
package com.amq.broker;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
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
    private static boolean isTransaction = true;
    private static int acknowledge = Session.AUTO_ACKNOWLEDGE;

    static {
        LOGGER.info("PooledConnectionFactory initializing.");
        cf.setConnectionFactory(new ActiveMQConnectionFactory(JmsComstant.URL));
        cf.setMaxConnections(2);
        cf.setMaximumActiveSessionPerConnection(100);
        cf.setBlockIfSessionPoolIsFull(true);
        LOGGER.info("ConnectionFactory initialized.");
    }

    public static Connection getConnection() {
        return getConnection(new IdGenerator("dayhr-client-id").generateId());
    }

    public static Connection getConnection(String clientId) {
        final CountDownLatch latch = new CountDownLatch(1);
        latch.getCount();
        Connection connection = null;
        try {
            connection = cf.createConnection();
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
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    LOGGER.error("close connection error ,error msg:", e);
                }
            }
        }
        return connection;
    }

    public static Session getSession() throws JMSException {
        return getSession(isTransaction, acknowledge);
    }

    public static Session getSession(boolean isTransaction, int acknowledge) throws JMSException {
        return getConnection().createSession(isTransaction, acknowledge);
    }

}


