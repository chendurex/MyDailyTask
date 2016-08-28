package com.amq.broker;

/**
 * @author chen
 * @description
 * @pachage com.amq.broker
 * @date 2016/8/25 9:59
 */
public class attention {
    /**
     * 1，先去看pool analysis的每个实现，了解每个属性，以及根据具体情况配置适合自己的属性
     *
     * 2，不管是connection、session、product、consumer、message都需要为每个对象设置一个唯一ID，用于溯源跟踪与消息重复验证
     *
     * 3，prefetch：控制消费者每次存储的消息数量，消费者跟broker中间有个缓冲队列，broker每次接收到producer的消息后会立即推送给consumer
     * 的缓冲队列，一直达到consumer的prefetch上限，才会停止推送，consumer将从缓存队里获取消息并消费，如果consumer的消费能力
     * 不高，那么会导致缓冲队列阻塞非常多的消息，而且如果设置了多consumer，那么可能导致A consumer堆积非常多消息，而B consumer
     * 没有任何可消费的消息，直到A consumer 关闭或者整个connection关闭，才有可能把消息从新分发给B consumer
     * 这时可以根据自己需求设置大小，如果消费能力低则设置为1，保证每次消费一条消息，
     * broker推送一条，防止消息在consumer堆积，具体设置请查看文档：http://activemq.apache.org/what-is-the-prefetch-limit-for.html
     * 以及出现的原因：http://activemq.apache.org/i-do-not-receive-messages-in-my-second-consumer.html
     * 另外因为这个prefetch间接影响的一个地方是配置了consumer pool，因为pool会重复利用consumer，正常情况close掉consumer
     * 则会直接把未消费的消息重新派发到其它的consumer或者broker存储，但是因为pool实际未关闭consumer，导致消息一直存储到缓冲池，
     * 知道consumer重新利用才会重新消息或者分派到其它的consumer，这里变相的出现一个假象就是：消息一直阻塞到consumer不消费
     * 根据当前问题：org.apache.activemq.pool.PooledConnectionFactory 连接池是不缓存consumer
     * 建议：如果使用了多consumer那么最好prefetch设置在20个以内，消费能力低的直接设置为1
     *
     */
}
