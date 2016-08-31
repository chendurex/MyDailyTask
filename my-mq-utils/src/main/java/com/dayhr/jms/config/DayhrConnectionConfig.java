package com.dayhr.jms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms.config
 * @date 2016/8/31 10:46
 */
class DayhrConnectionConfig {
    static Logger LOGGER = LoggerFactory.getLogger(DayhrConnectionConfig.class);
    private static int maxConnections;
    private static int maximumActiveSessionPerConnection;
    private static long idleTimeout;
    private static boolean blockIfSessionPoolIsFull;
    private static long blockIfSessionPoolIsFullTimeout;
    private static boolean reconnectOnException;

    static {
        try {
            maxConnections = LoadConfigUtils.findIntValueFromProperties("connection.maxConnections");
            maximumActiveSessionPerConnection = LoadConfigUtils.findIntValueFromProperties("connection.maximumActiveSessionPerConnection");
            idleTimeout = LoadConfigUtils.findLongValueFromProperties("connection.idleTimeout");
            blockIfSessionPoolIsFull = LoadConfigUtils.findBooleanValueFromProperties("connection.blockIfSessionPoolIsFull");
            reconnectOnException = LoadConfigUtils.findBooleanValueFromProperties("connection.reconnectOnException");
            blockIfSessionPoolIsFullTimeout = LoadConfigUtils.findLongValueFromProperties("connection.blockIfSessionPoolIsFullTimeout");
        } catch (Exception e) {
            LOGGER.error("获取configProp属性失败，堆栈信息为：", e);
        }
    }

    public static int getMaxConnections() {
        return maxConnections;
    }

    public static void setMaxConnections(int maxConnections) {
        DayhrConnectionConfig.maxConnections = maxConnections;
    }

    public static int getMaximumActiveSessionPerConnection() {
        return maximumActiveSessionPerConnection;
    }

    public static void setMaximumActiveSessionPerConnection(int maximumActiveSessionPerConnection) {
        DayhrConnectionConfig.maximumActiveSessionPerConnection = maximumActiveSessionPerConnection;
    }

    public static long getIdleTimeout() {
        return idleTimeout;
    }

    public static void setIdleTimeout(long idleTimeout) {
        DayhrConnectionConfig.idleTimeout = idleTimeout;
    }

    public static boolean isBlockIfSessionPoolIsFull() {
        return blockIfSessionPoolIsFull;
    }

    public static void setBlockIfSessionPoolIsFull(boolean blockIfSessionPoolIsFull) {
        DayhrConnectionConfig.blockIfSessionPoolIsFull = blockIfSessionPoolIsFull;
    }

    public static long getBlockIfSessionPoolIsFullTimeout() {
        return blockIfSessionPoolIsFullTimeout;
    }

    public static void setBlockIfSessionPoolIsFullTimeout(long blockIfSessionPoolIsFullTimeout) {
        DayhrConnectionConfig.blockIfSessionPoolIsFullTimeout = blockIfSessionPoolIsFullTimeout;
    }

    public static boolean isReconnectOnException() {
        return reconnectOnException;
    }

    public static void setReconnectOnException(boolean reconnectOnException) {
        DayhrConnectionConfig.reconnectOnException = reconnectOnException;
    }
}
