package com.amq.pool.analysis;

/**
 * package com.amq.pool.analysis
 * description 介绍ActiveMQConnectionFactory实现原理
 * Created by chen on 2016/8/28.
 * 1，定义connection默认的host是localhost，端口是61616，用户可以设置系统属性来设置默认值，org.apache.activemq.AMQ_HOST或者
 *    AMQ_HOST设置host，org.apache.activemq.AMQ_PORT或者AMQ_PORT设置端口号,
 *    broker默认是tcp://localhost:61616，用户可以设置org.apache.activemq.BROKER_BIND_URL或者BROKER_BIND_URL修改默认连接
 *
 * 2，ActiveMQConnectionFactory提供5个构造方法，提供给用户的构造方法主要是提供一个broker、username、password等
 *
 * 3，broker在填写的时候一定要根据文档提供的相关配置填写，如果填写错误是直接抛出参数异常，而不是忽略错误的配置
 *
 * 4，createConnection方法是构造一个connection，实现是ActiveMQConnection，默认提供的是topicConnection，如果需要提供QueueConnection
 *    那么需要显示的调用createQueueConnection，所以最好需要什么connection则创建什么连接，不要直接使用默认值，防止出现不可预知错误
 *
 * 5，工厂就是一个简单的代理实现，所以实现非常简单，重点还是来分析下工厂的每个配置属性作用：
 * // 基本配置
 *
 * 5.1，userName：默认null，连接的用户名
 *
 * 5.2，password：默认null，连接的密码
 *
 * 5.3，brokerURL：默认null，连接到amq broker的uri，可以是普通的tcp或者负载版的failover
 *
 * 5.4，clientID：默认null，每个链接的客户端ID，这个ID最好是设置，作用两点：1，可以方便跟踪每个消息的来源。
 *      2，持久订阅时，必须是clientId+topic合并 才能确定一个唯一ID，所以为了以后扩展每次都设置。
 *      默认是采用getClassName方式设置，保证可以很清晰知道是哪个类产生的消息做负载的时候也方便扩展
 *
 * 5.5，maxThreadPoolSize：
 *
 * 5.6，closeTimeout：连接关闭超时，默认15000ms。client close掉connection时，是需要向broker发送close的请求，假如broker被阻塞
 *      或者已经宕机，那么请求无法响应，直接影响client连接无法释放，设置超时时间则可以防止此事发生
 *
 * 5.7，sendTimeout：发送消息超时设置，默认0不超时。如果>0，那么到达超时时间后，消息发送失败
 *
 * 5.8，connectResponseTimeout：连接超时设置，默认0不超时。如果>0，那么请求连接达到超时时间后，连接提示失败，否则会一直阻塞直到连接成功。
 *
 * 5.9，objectMessageSerializationDefered：When an object is set on an ObjectMessage,
 *      the JMS spec requires the object to be serialized by that set method. Enabling this flag causes the object to not get serialized.
 *      The object may subsequently get serialized if the message needs to be sent over a socket or stored to disk.
 *
 * 5.10，statsEnabled：是否分析连接状态，默认false。开启模式，会有连接监听每个连接的状态，并分析结果，再开启一个消费者监听消息
 *
 * 5.11，dispatchAsync：broker异步发送消息到consumer，默认是false。如果设置为true，那么每次发送消息都是创建一个线程发送消息到
 *       consumer，对于消费能力非常低的，可以使用异步，防止broker被其中的某个consumer阻塞无法返回。但是一般情况还是建议采用同步，
 *       如果消费能力高的，采用同步可以更加快速的返回，并且减少线程的创建的开销与上下文切换。amq提供consumer级别的分发方式，对于
 *       具体的consumer做具体的策略。http://activemq.apache.org/consumer-dispatch-async.html
 *
 * 5.12，useAsyncSend：是否使用异步发送消息，默认false。如果使用异步那么消息发送出去会立即返回。在有事物或者消息安全性非常高的
 *       情况下，最好是使用同步，保证消息的可靠性。如果是对于高性能，但是消息可以丢失的情况，那么使用异步也是不错的选择
 *
 * 5.13，sendAcksAsync：
 *
 * 5.14，alwaysSyncSend：是否全部使用异步发送消息，默认false。这个跟useAsyncSend类似，但是有一点不同，useAsyncSend还是会获取
 *       broker的消息回复从而再次做进异步处理，而alwaysSyncSend相当于把消息丢到broker则后续可以不管了，有点类似UDP连接
 *
 * 5.15，alwaysSessionAsync：是否使用异步session，默认true。使用异步则表示每个session都是一个线程在处理(需要控制下线程池大小)
 *
 * 5.16，watchTopicAdvisories：
 * 5.17，checkForDuplicates：检查消息是否重复，默认true，主要是根据messageId做重复性检查，所以建议每天消息都带上messageId
 *
 * 5.18，useRetroactiveConsumer：是否启用溯源消息，默认false。在 non-durable topic subscribers订阅topic时，topic之前发送的
 *       消息是否也推送给当前consumer
 *
 * 5.19，optimizeAcknowledge：是否批量响应请求：默认false。如果开启，那么会批量的响应broker的消费请求。在要求可靠性的时，还是不要开启
 *
 * 5.20，optimizedMessageDispatch：是否扩充prefetch最大值，默认true。如果消费能力非常的高，则提高prefetch的缓存内容以达到
 *       更高的吞吐量(仅限于 durable topic subscribers)
 *
 * 5.21，useCompression：是否启用压缩消息体，默认false。如果发送的消息非常的大的话，可以启用压缩的消息
 *
 * 5.22，producerWindowSize：每次发送消息的大小，默认0，就是不限制。如果在多线程使用同一个连接的情况下，而且发送的数据比较大，
 *       会导致连接被阻塞，显式的控制大小可以保证稳定性。http://activemq.apache.org/producer-flow-control.html
 *
 * 5.23，copyMessageOnSend：是否发送一个消息的副本给broker，默认true。如果设置为true，那么每次都是复制了一份message的副本给
 *       broker，保证原始的message的不可变性。(这里我也没明白为什么要复制一个副本，跟踪到源码后，发现仅仅是设置了一些感觉没用
 *       的东西，而且消息发送出去后，再次的修改也不会影响到原来的消息的状态，难道是因为并发可能出现的问题？)
 *
 * 5.24，disableTimeStampsByDefault：是否发送timeStamp时间戳，默认true。如果设置false，那么发送的消息不带有时间戳，这个时间是
 *       broker 设置的，因为发送消息可能出现延迟，所以时间也可能出现延误。数据量大的情况，而且对于时间戳可以不使用的情况，可以关闭
 *
 * 5.25，prefetchPolicy：prefetch代理对象，默认实现ActiveMQPrefetchPolicy。http://activemq.apache.org/what-is-the-prefetch-limit-for.html
 *
 * 5.26，redeliveryPolicy：异常消息重发模式，默认实现是RedeliveryPolicy。主要是事物型消息出现异常，消息会被存储到一个临时队列，
 *       然后再由broker重新发送，具体策略可以查看http://activemq.apache.org/redelivery-policy.html
 *
 * 5.27，nonBlockingRedelivery：异常消息发送方式，默认阻塞。与redeliveryPolicy配合功能
 *
 * 5.28，blobTransferPolicy：大文本数据发送模式，默认实现BlobTransferPolicy。
 *
 * 5.29，messagePrioritySupported：
 * 5.30，transactedIndividualAck：
 * 5.31，auditMaximumProducerNumber：default 64， Maximum number of producers that will be audited
 * 5.32，auditDepth：default 2048，The size of the message window that will be audited (for duplicates and out of order messages
 * 5.33，consumerFailoverRedeliveryWaitPeriod：
 * 5.34，rmIdFromConnectionId：
 * 5.35，consumerExpiryCheckEnabled：检查消息是否过期，默认true。若不开启，则会消费已经过期的消息
 */
public interface ActiveMQConnectionFactoryImpl {
}
