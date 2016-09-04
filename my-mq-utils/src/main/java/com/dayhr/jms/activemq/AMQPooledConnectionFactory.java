
package com.dayhr.jms.activemq;

import com.dayhr.config.DayhrDefaultConnectionConfig;
import com.dayhr.config.DayhrUserConnectionConfig;
import com.dayhr.config.LoadConfigUtils;
import com.dayhr.jms.DayhrConnection;
import com.dayhr.jms.DayhrPooledConnectionFactory;
import com.dayhr.jms.DayhrQueueConnection;
import com.dayhr.jms.DayhrTopicConnection;
import com.dayhr.support.DayhrMQUtils;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:04
 */
public class AMQPooledConnectionFactory implements DayhrPooledConnectionFactory {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private boolean isPrintConfigProperties;
    private final PooledConnectionFactory factory = new PooledConnectionFactory();

    public AMQPooledConnectionFactory() {
        this(DayhrDefaultConnectionConfig.getInstance());
    }

    public AMQPooledConnectionFactory(DayhrDefaultConnectionConfig config) {
        configFactory(config);
    }

    @Override
    public DayhrConnection createConnection() throws JMSException{
        return createConnection(null);
    }

    @Override
    public DayhrConnection createConnection(DayhrUserConnectionConfig userConfig) throws JMSException {
        LOGGER.info("dayhr mq 在 {} 准备创建连接\n", DayhrMQUtils.currentTime());
        long startTime = System.currentTimeMillis();
        AMQConnection connection;
        if (userConfig == null) {
            connection = new AMQConnection((ActiveMQConnection)factory.createConnection());
        } else {
            connection = new AMQConnection((ActiveMQConnection)factory.createConnection(), userConfig);
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("dayhr mq 在 {} 创建连接成功，共花费：{}毫秒 \n", DayhrMQUtils.currentTime(), (endTime - startTime));
        if (isPrintConfigProperties) {
            LoadConfigUtils.printConfigValue(userConfig);
        }
        return connection;
    }

    @Override
    public DayhrQueueConnection createQueueConnection() throws JMSException {
        return (DayhrQueueConnection)createConnection();
    }

    @Override
    public DayhrQueueConnection createQueueConnection(DayhrUserConnectionConfig userConfig) throws JMSException {
        return (DayhrQueueConnection)createConnection(userConfig);
    }

    @Override
    public DayhrTopicConnection createTopicConnection() throws JMSException {
        return (DayhrTopicConnection) createConnection();
    }

    @Override
    public DayhrTopicConnection createTopicConnection(DayhrUserConnectionConfig userConfig) throws JMSException {
        return (DayhrTopicConnection) createConnection(userConfig);
    }

    private void configFactory(DayhrDefaultConnectionConfig config) {
        isPrintConfigProperties = config.isPrintConfigProperties();

        factory.setConnectionFactory(new ActiveMQConnectionFactory());
        factory.setMaxConnections(config.getMaxConnections());
        factory.setMaximumActiveSessionPerConnection(config.getMaximumActiveSessionPerConnection());
        factory.setIdleTimeout(config.getIdleTimeout());
        factory.setBlockIfSessionPoolIsFull(config.isBlockIfSessionPoolIsFull());
        factory.setBlockIfSessionPoolIsFullTimeout(config.getBlockIfSessionPoolIsFullTimeout());
        factory.setReconnectOnException(config.isReconnectOnException());
    }
}
