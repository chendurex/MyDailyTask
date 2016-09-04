package com.dayhr.jms.activemq;

import com.dayhr.config.DayhrUserConnectionConfig;
import com.dayhr.jms.DayhrConnection;
import com.dayhr.jms.DayhrQueueConnection;
import com.dayhr.jms.DayhrTopicConnection;
import org.apache.activemq.ActiveMQConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:05
 */
public class AMQConnection implements DayhrConnection, DayhrTopicConnection, DayhrQueueConnection {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final ActiveMQConnection connection;
    public AMQConnection(ActiveMQConnection connection) {
        this.connection = connection;
    }

    public AMQConnection(ActiveMQConnection connection, DayhrUserConnectionConfig userConfig) {
        this.connection = connection;
        setUserConfig(userConfig);
    }

    @Override
    public void setUserConfig(DayhrUserConnectionConfig userConfig) {
        connection.setAlwaysSessionAsync(userConfig.isAlwaysSessionAsync());
        connection.setDispatchAsync(userConfig.isDispatchAsync());
        connection.setPrefetchPolicy(userConfig.getPrefetchPolicy());
        connection.setRedeliveryPolicyMap(userConfig.getRedeliveryPolicyMap());
        connection.setBlobTransferPolicy(userConfig.getBlobTransferPolicy());
        connection.setTransformer(userConfig.getTransformer());
        connection.setDisableTimeStampsByDefault(userConfig.isDisableTimeStampsByDefault());
        connection.setOptimizeAcknowledgeTimeOut(userConfig.getOptimizeAcknowledgeTimeOut());
        connection.setOptimizedAckScheduledAckInterval(userConfig.getOptimizedAckScheduledAckInterval());
        connection.setCopyMessageOnSend(userConfig.isCopyMessageOnSend());
        connection.setUseCompression(userConfig.isUseCompression());
        connection.setObjectMessageSerializationDefered(userConfig.isObjectMessageSerializationDefered());
        connection.setUseAsyncSend(userConfig.isUseAsyncSend());
        connection.setOptimizeAcknowledge(userConfig.isOptimizeAcknowledge());
        connection.setUseRetroactiveConsumer(userConfig.isUseRetroactiveConsumer());
        connection.setExclusiveConsumer(userConfig.isExclusiveConsumer());
        connection.setAlwaysSyncSend(userConfig.isAlwaysSyncSend());
        connection.setWatchTopicAdvisories(userConfig.isWatchTopicAdvisories());
        connection.setProducerWindowSize(userConfig.getProducerWindowSize());
        connection.setWarnAboutUnstartedConnectionTimeout(userConfig.getWarnAboutUnstartedConnectionTimeout());

        connection.setSendAcksAsync(userConfig.isSendAcksAsync());
        connection.setSendTimeout(userConfig.getSendTimeout());
        connection.setConnectResponseTimeout(userConfig.getConnectResponseTimeout());
        try {
            connection.setExceptionListener(userConfig.getExceptionListener());
        } catch (JMSException e) {
            LOGGER.error("设置 connection exceptionListenner失败，当前堆栈信息为：", e);
        }
        connection.setAuditMaximumProducerNumber(userConfig.getAuditMaximumProducerNumber());
        connection.setConsumerFailoverRedeliveryWaitPeriod(userConfig.getConsumerFailoverRedeliveryWaitPeriod());
        connection.setCheckForDuplicates(userConfig.isCheckForDuplicates());
        connection.setClientInternalExceptionListener(userConfig.getClientInternalExceptionListener());
        connection.setMessagePrioritySupported(userConfig.isMessagePrioritySupported());
        connection.setTransactedIndividualAck(userConfig.isTransactedIndividualAck());
        connection.setNonBlockingRedelivery(userConfig.isNonBlockingRedelivery());
        connection.setMaxThreadPoolSize(userConfig.getMaxThreadPoolSize());
        connection.setRejectedTaskHandler(userConfig.getRejectedTaskHandler());
        connection.setRmIdFromConnectionId(userConfig.isRmIdFromConnectionId());
        connection.setConsumerExpiryCheckEnabled(userConfig.isConsumerExpiryCheckEnabled());
        connection.setTrustedPackages(userConfig.getTrustedPackages());
        connection.setTrustAllPackages(userConfig.isTrustAllPackages());
    }
}
