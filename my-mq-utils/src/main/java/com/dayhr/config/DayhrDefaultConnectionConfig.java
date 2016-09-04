package com.dayhr.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.config
 * @date 2016/8/31 10:46
 */
public class DayhrDefaultConnectionConfig {
    private static final DayhrDefaultConnectionConfig DEFAULT_CONNECTION_CONFIG = new DayhrDefaultConnectionConfig();
    static Logger LOGGER = LoggerFactory.getLogger(DayhrDefaultConnectionConfig.class);
    private boolean isPrintConfigProperties;
    private int maxConnections;
    private int maximumActiveSessionPerConnection;
    private int idleTimeout;
    private boolean blockIfSessionPoolIsFull;
    private long blockIfSessionPoolIsFullTimeout;
    private boolean reconnectOnException;

    private DayhrDefaultConnectionConfig() {}

    public static DayhrDefaultConnectionConfig getInstance() {
        return DEFAULT_CONNECTION_CONFIG;
    }
    {
        try {
            isPrintConfigProperties = LoadConfigUtils.findBooleanValueFromProperties("connection.isPrintConfigProperties");
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

    public int getMaximumActiveSessionPerConnection() {
        return maximumActiveSessionPerConnection;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public boolean isBlockIfSessionPoolIsFull() {
        return blockIfSessionPoolIsFull;
    }

    public long getBlockIfSessionPoolIsFullTimeout() {
        return blockIfSessionPoolIsFullTimeout;
    }

    public boolean isReconnectOnException() {
        return reconnectOnException;
    }

    public boolean isPrintConfigProperties() {
        return isPrintConfigProperties;
    }
}
