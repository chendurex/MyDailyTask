package com.dayhr.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.config
 * @date 2016/8/31 10:46
 */
public class DayhrConnectionConfig {
    static Logger LOGGER = LoggerFactory.getLogger(DayhrConnectionConfig.class);
    private int maxConnections;
    private int maximumActiveSessionPerConnection;
    private int idleTimeout;
    private boolean blockIfSessionPoolIsFull;
    private long blockIfSessionPoolIsFullTimeout;
    private boolean reconnectOnException;

    {
        try {
            maxConnections = LoadConfigUtils.findIntValueFromProperties("connection.maxConnections");
            maximumActiveSessionPerConnection = LoadConfigUtils.findIntValueFromProperties("connection.maximumActiveSessionPerConnection");
            idleTimeout = LoadConfigUtils.findIntValueFromProperties("connection.idleTimeout");
            blockIfSessionPoolIsFull = LoadConfigUtils.findBooleanValueFromProperties("connection.blockIfSessionPoolIsFull");
            reconnectOnException = LoadConfigUtils.findBooleanValueFromProperties("connection.reconnectOnException");
            blockIfSessionPoolIsFullTimeout = LoadConfigUtils.findLongValueFromProperties("connection.blockIfSessionPoolIsFullTimeout");
        } catch (Exception e) {
            LOGGER.error("获取configProp属性失败，堆栈信息为：", e);
        }
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaximumActiveSessionPerConnection() {
        return maximumActiveSessionPerConnection;
    }

    public void setMaximumActiveSessionPerConnection(int maximumActiveSessionPerConnection) {
        this.maximumActiveSessionPerConnection = maximumActiveSessionPerConnection;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public boolean isBlockIfSessionPoolIsFull() {
        return blockIfSessionPoolIsFull;
    }

    public void setBlockIfSessionPoolIsFull(boolean blockIfSessionPoolIsFull) {
        this.blockIfSessionPoolIsFull = blockIfSessionPoolIsFull;
    }

    public long getBlockIfSessionPoolIsFullTimeout() {
        return blockIfSessionPoolIsFullTimeout;
    }

    public void setBlockIfSessionPoolIsFullTimeout(long blockIfSessionPoolIsFullTimeout) {
        this.blockIfSessionPoolIsFullTimeout = blockIfSessionPoolIsFullTimeout;
    }

    public boolean isReconnectOnException() {
        return reconnectOnException;
    }

    public void setReconnectOnException(boolean reconnectOnException) {
        this.reconnectOnException = reconnectOnException;
    }
}
