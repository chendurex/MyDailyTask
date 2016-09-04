package com.dayhr.config;

import org.apache.activemq.*;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.management.JMSStatsImpl;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.util.ClassLoadingAwareObjectInputStream;
import org.apache.activemq.util.IdGenerator;

import javax.jms.ExceptionListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author chen
 * @date 2016/9/4 22:10
 */
public class DayhrUserConnectionConfig  {
    final DayhrDefaultConnectionConfig DEFAULT_CONFIG = DayhrDefaultConnectionConfig.getInstance();

    // dayhr默认的配置,也可以根据需求再次修改
    private int maxConnections;
    private int maximumActiveSessionPerConnection;
    private int idleTimeout;
    private boolean blockIfSessionPoolIsFull;
    private long blockIfSessionPoolIsFullTimeout;
    private boolean reconnectOnException;

    // dayhr未提供的配置，使用的amq的默认配置，根据需求可以再次修改
    public static final int DEFAULT_PRODUCER_WINDOW_SIZE = 0;
    private String clientID;
    protected boolean dispatchAsync=true;
    protected boolean alwaysSessionAsync=true;

    JMSStatsImpl factoryStats = new JMSStatsImpl();

    private IdGenerator clientIdGenerator;
    private String clientIDPrefix;
    private IdGenerator connectionIdGenerator;
    private String connectionIDPrefix;

    // client policies
    private ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
    private RedeliveryPolicyMap redeliveryPolicyMap = new RedeliveryPolicyMap();
    {
        redeliveryPolicyMap.setDefaultEntry(new RedeliveryPolicy());
    }
    private BlobTransferPolicy blobTransferPolicy = new BlobTransferPolicy();
    private MessageTransformer transformer;

    private boolean disableTimeStampsByDefault;
    private boolean optimizedMessageDispatch = true;
    private long optimizeAcknowledgeTimeOut = 300;
    private long optimizedAckScheduledAckInterval = 0;
    private boolean copyMessageOnSend = true;
    private boolean useCompression;
    private boolean objectMessageSerializationDefered;
    private boolean useAsyncSend;
    private boolean optimizeAcknowledge;
    private int closeTimeout = 15000;
    private boolean useRetroactiveConsumer;
    private boolean exclusiveConsumer;
    private boolean nestedMapAndListEnabled = true;
    private boolean alwaysSyncSend;
    private boolean watchTopicAdvisories = true;
    private int producerWindowSize = DEFAULT_PRODUCER_WINDOW_SIZE;
    private long warnAboutUnstartedConnectionTimeout = 500L;
    private int sendTimeout = 0;
    private int connectResponseTimeout = 0;
    private boolean sendAcksAsync=true;
    private TransportListener transportListener;
    private ExceptionListener exceptionListener;
    private int auditDepth = ActiveMQMessageAudit.DEFAULT_WINDOW_SIZE;
    private int auditMaximumProducerNumber = ActiveMQMessageAudit.MAXIMUM_PRODUCER_COUNT;
    private boolean useDedicatedTaskRunner;
    private long consumerFailoverRedeliveryWaitPeriod = 0;
    private boolean checkForDuplicates = true;
    private ClientInternalExceptionListener clientInternalExceptionListener;
    private boolean messagePrioritySupported = false;
    private boolean transactedIndividualAck = false;
    private boolean nonBlockingRedelivery = false;
    private int maxThreadPoolSize = ActiveMQConnection.DEFAULT_THREAD_POOL_SIZE;
    private TaskRunnerFactory sessionTaskRunner;
    private RejectedExecutionHandler rejectedTaskHandler = null;
    protected int xaAckMode = -1; // ensure default init before setting via brokerUrl introspection in sub class
    private boolean rmIdFromConnectionId = false;
    private boolean consumerExpiryCheckEnabled = true;
    private List<String> trustedPackages = Arrays.asList(ClassLoadingAwareObjectInputStream.serializablePackages);
    private boolean trustAllPackages = false;


    private DayhrUserConnectionConfig() {
    }

    public static DayhrUserConnectionConfig getInstance() {
        DayhrUserConnectionConfig userConfig = new DayhrUserConnectionConfig();
        userConfig.defaultConfig();
        return userConfig;
    }

    public void defaultConfig() {
        setMaxConnections(DEFAULT_CONFIG.getMaxConnections());
        setMaximumActiveSessionPerConnection(DEFAULT_CONFIG.getMaximumActiveSessionPerConnection());
        setIdleTimeout(DEFAULT_CONFIG.getIdleTimeout());
        setBlockIfSessionPoolIsFull(DEFAULT_CONFIG.isBlockIfSessionPoolIsFull());
        setBlockIfSessionPoolIsFullTimeout(DEFAULT_CONFIG.getBlockIfSessionPoolIsFullTimeout());
        setReconnectOnException(DEFAULT_CONFIG.isReconnectOnException());
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

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public boolean isDispatchAsync() {
        return dispatchAsync;
    }

    public void setDispatchAsync(boolean dispatchAsync) {
        this.dispatchAsync = dispatchAsync;
    }

    public boolean isAlwaysSessionAsync() {
        return alwaysSessionAsync;
    }

    public void setAlwaysSessionAsync(boolean alwaysSessionAsync) {
        this.alwaysSessionAsync = alwaysSessionAsync;
    }

    public JMSStatsImpl getFactoryStats() {
        return factoryStats;
    }

    public void setFactoryStats(JMSStatsImpl factoryStats) {
        this.factoryStats = factoryStats;
    }

    public IdGenerator getClientIdGenerator() {
        return clientIdGenerator;
    }

    public void setClientIdGenerator(IdGenerator clientIdGenerator) {
        this.clientIdGenerator = clientIdGenerator;
    }

    public String getClientIDPrefix() {
        return clientIDPrefix;
    }

    public void setClientIDPrefix(String clientIDPrefix) {
        this.clientIDPrefix = clientIDPrefix;
    }

    public IdGenerator getConnectionIdGenerator() {
        return connectionIdGenerator;
    }

    public void setConnectionIdGenerator(IdGenerator connectionIdGenerator) {
        this.connectionIdGenerator = connectionIdGenerator;
    }

    public String getConnectionIDPrefix() {
        return connectionIDPrefix;
    }

    public void setConnectionIDPrefix(String connectionIDPrefix) {
        this.connectionIDPrefix = connectionIDPrefix;
    }

    public ActiveMQPrefetchPolicy getPrefetchPolicy() {
        return prefetchPolicy;
    }

    public void setPrefetchPolicy(ActiveMQPrefetchPolicy prefetchPolicy) {
        this.prefetchPolicy = prefetchPolicy;
    }

    public RedeliveryPolicyMap getRedeliveryPolicyMap() {
        return redeliveryPolicyMap;
    }

    public void setRedeliveryPolicyMap(RedeliveryPolicyMap redeliveryPolicyMap) {
        this.redeliveryPolicyMap = redeliveryPolicyMap;
    }

    public BlobTransferPolicy getBlobTransferPolicy() {
        return blobTransferPolicy;
    }

    public void setBlobTransferPolicy(BlobTransferPolicy blobTransferPolicy) {
        this.blobTransferPolicy = blobTransferPolicy;
    }

    public MessageTransformer getTransformer() {
        return transformer;
    }

    public void setTransformer(MessageTransformer transformer) {
        this.transformer = transformer;
    }

    public boolean isDisableTimeStampsByDefault() {
        return disableTimeStampsByDefault;
    }

    public void setDisableTimeStampsByDefault(boolean disableTimeStampsByDefault) {
        this.disableTimeStampsByDefault = disableTimeStampsByDefault;
    }

    public boolean isOptimizedMessageDispatch() {
        return optimizedMessageDispatch;
    }

    public void setOptimizedMessageDispatch(boolean optimizedMessageDispatch) {
        this.optimizedMessageDispatch = optimizedMessageDispatch;
    }

    public long getOptimizeAcknowledgeTimeOut() {
        return optimizeAcknowledgeTimeOut;
    }

    public void setOptimizeAcknowledgeTimeOut(long optimizeAcknowledgeTimeOut) {
        this.optimizeAcknowledgeTimeOut = optimizeAcknowledgeTimeOut;
    }

    public long getOptimizedAckScheduledAckInterval() {
        return optimizedAckScheduledAckInterval;
    }

    public void setOptimizedAckScheduledAckInterval(long optimizedAckScheduledAckInterval) {
        this.optimizedAckScheduledAckInterval = optimizedAckScheduledAckInterval;
    }

    public boolean isCopyMessageOnSend() {
        return copyMessageOnSend;
    }

    public void setCopyMessageOnSend(boolean copyMessageOnSend) {
        this.copyMessageOnSend = copyMessageOnSend;
    }

    public boolean isUseCompression() {
        return useCompression;
    }

    public void setUseCompression(boolean useCompression) {
        this.useCompression = useCompression;
    }

    public boolean isObjectMessageSerializationDefered() {
        return objectMessageSerializationDefered;
    }

    public void setObjectMessageSerializationDefered(boolean objectMessageSerializationDefered) {
        this.objectMessageSerializationDefered = objectMessageSerializationDefered;
    }

    public boolean isUseAsyncSend() {
        return useAsyncSend;
    }

    public void setUseAsyncSend(boolean useAsyncSend) {
        this.useAsyncSend = useAsyncSend;
    }

    public boolean isOptimizeAcknowledge() {
        return optimizeAcknowledge;
    }

    public void setOptimizeAcknowledge(boolean optimizeAcknowledge) {
        this.optimizeAcknowledge = optimizeAcknowledge;
    }

    public int getCloseTimeout() {
        return closeTimeout;
    }

    public void setCloseTimeout(int closeTimeout) {
        this.closeTimeout = closeTimeout;
    }

    public boolean isUseRetroactiveConsumer() {
        return useRetroactiveConsumer;
    }

    public void setUseRetroactiveConsumer(boolean useRetroactiveConsumer) {
        this.useRetroactiveConsumer = useRetroactiveConsumer;
    }

    public boolean isExclusiveConsumer() {
        return exclusiveConsumer;
    }

    public void setExclusiveConsumer(boolean exclusiveConsumer) {
        this.exclusiveConsumer = exclusiveConsumer;
    }

    public boolean isNestedMapAndListEnabled() {
        return nestedMapAndListEnabled;
    }

    public void setNestedMapAndListEnabled(boolean nestedMapAndListEnabled) {
        this.nestedMapAndListEnabled = nestedMapAndListEnabled;
    }

    public boolean isAlwaysSyncSend() {
        return alwaysSyncSend;
    }

    public void setAlwaysSyncSend(boolean alwaysSyncSend) {
        this.alwaysSyncSend = alwaysSyncSend;
    }

    public boolean isWatchTopicAdvisories() {
        return watchTopicAdvisories;
    }

    public void setWatchTopicAdvisories(boolean watchTopicAdvisories) {
        this.watchTopicAdvisories = watchTopicAdvisories;
    }

    public int getProducerWindowSize() {
        return producerWindowSize;
    }

    public void setProducerWindowSize(int producerWindowSize) {
        this.producerWindowSize = producerWindowSize;
    }

    public long getWarnAboutUnstartedConnectionTimeout() {
        return warnAboutUnstartedConnectionTimeout;
    }

    public void setWarnAboutUnstartedConnectionTimeout(long warnAboutUnstartedConnectionTimeout) {
        this.warnAboutUnstartedConnectionTimeout = warnAboutUnstartedConnectionTimeout;
    }

    public int getSendTimeout() {
        return sendTimeout;
    }

    public void setSendTimeout(int sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public int getConnectResponseTimeout() {
        return connectResponseTimeout;
    }

    public void setConnectResponseTimeout(int connectResponseTimeout) {
        this.connectResponseTimeout = connectResponseTimeout;
    }

    public boolean isSendAcksAsync() {
        return sendAcksAsync;
    }

    public void setSendAcksAsync(boolean sendAcksAsync) {
        this.sendAcksAsync = sendAcksAsync;
    }

    public TransportListener getTransportListener() {
        return transportListener;
    }

    public void setTransportListener(TransportListener transportListener) {
        this.transportListener = transportListener;
    }

    public ExceptionListener getExceptionListener() {
        return exceptionListener;
    }

    public void setExceptionListener(ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    public int getAuditDepth() {
        return auditDepth;
    }

    public void setAuditDepth(int auditDepth) {
        this.auditDepth = auditDepth;
    }

    public int getAuditMaximumProducerNumber() {
        return auditMaximumProducerNumber;
    }

    public void setAuditMaximumProducerNumber(int auditMaximumProducerNumber) {
        this.auditMaximumProducerNumber = auditMaximumProducerNumber;
    }

    public boolean isUseDedicatedTaskRunner() {
        return useDedicatedTaskRunner;
    }

    public void setUseDedicatedTaskRunner(boolean useDedicatedTaskRunner) {
        this.useDedicatedTaskRunner = useDedicatedTaskRunner;
    }

    public long getConsumerFailoverRedeliveryWaitPeriod() {
        return consumerFailoverRedeliveryWaitPeriod;
    }

    public void setConsumerFailoverRedeliveryWaitPeriod(long consumerFailoverRedeliveryWaitPeriod) {
        this.consumerFailoverRedeliveryWaitPeriod = consumerFailoverRedeliveryWaitPeriod;
    }

    public boolean isCheckForDuplicates() {
        return checkForDuplicates;
    }

    public void setCheckForDuplicates(boolean checkForDuplicates) {
        this.checkForDuplicates = checkForDuplicates;
    }

    public ClientInternalExceptionListener getClientInternalExceptionListener() {
        return clientInternalExceptionListener;
    }

    public void setClientInternalExceptionListener(ClientInternalExceptionListener clientInternalExceptionListener) {
        this.clientInternalExceptionListener = clientInternalExceptionListener;
    }

    public boolean isMessagePrioritySupported() {
        return messagePrioritySupported;
    }

    public void setMessagePrioritySupported(boolean messagePrioritySupported) {
        this.messagePrioritySupported = messagePrioritySupported;
    }

    public boolean isTransactedIndividualAck() {
        return transactedIndividualAck;
    }

    public void setTransactedIndividualAck(boolean transactedIndividualAck) {
        this.transactedIndividualAck = transactedIndividualAck;
    }

    public boolean isNonBlockingRedelivery() {
        return nonBlockingRedelivery;
    }

    public void setNonBlockingRedelivery(boolean nonBlockingRedelivery) {
        this.nonBlockingRedelivery = nonBlockingRedelivery;
    }

    public int getMaxThreadPoolSize() {
        return maxThreadPoolSize;
    }

    public void setMaxThreadPoolSize(int maxThreadPoolSize) {
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

    public TaskRunnerFactory getSessionTaskRunner() {
        return sessionTaskRunner;
    }

    public void setSessionTaskRunner(TaskRunnerFactory sessionTaskRunner) {
        this.sessionTaskRunner = sessionTaskRunner;
    }

    public RejectedExecutionHandler getRejectedTaskHandler() {
        return rejectedTaskHandler;
    }

    public void setRejectedTaskHandler(RejectedExecutionHandler rejectedTaskHandler) {
        this.rejectedTaskHandler = rejectedTaskHandler;
    }

    public int getXaAckMode() {
        return xaAckMode;
    }

    public void setXaAckMode(int xaAckMode) {
        this.xaAckMode = xaAckMode;
    }

    public boolean isRmIdFromConnectionId() {
        return rmIdFromConnectionId;
    }

    public void setRmIdFromConnectionId(boolean rmIdFromConnectionId) {
        this.rmIdFromConnectionId = rmIdFromConnectionId;
    }

    public boolean isConsumerExpiryCheckEnabled() {
        return consumerExpiryCheckEnabled;
    }

    public void setConsumerExpiryCheckEnabled(boolean consumerExpiryCheckEnabled) {
        this.consumerExpiryCheckEnabled = consumerExpiryCheckEnabled;
    }

    public List<String> getTrustedPackages() {
        return trustedPackages;
    }

    public void setTrustedPackages(List<String> trustedPackages) {
        this.trustedPackages = trustedPackages;
    }

    public boolean isTrustAllPackages() {
        return trustAllPackages;
    }

    public void setTrustAllPackages(boolean trustAllPackages) {
        this.trustAllPackages = trustAllPackages;
    }
}
