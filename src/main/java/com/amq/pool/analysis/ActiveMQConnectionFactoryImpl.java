package com.amq.pool.analysis;

/**
 * package com.amq.pool.analysis
 * description 介绍ActiveMQConnectionFactory实现原理
 * Created by chen on 2016/8/28.
 * 1，定义connection默认的host是localhost，端口是61616，用户可以设置系统属性来设置默认值，org.apache.activemq.AMQ_HOST或者
 *    AMQ_HOST设置host，org.apache.activemq.AMQ_PORT或者AMQ_PORT设置端口号,
 *    broker默认是tcp://localhost:61616，用户可以设置org.apache.activemq.BROKER_BIND_URL或者BROKER_BIND_URL修改默认连接
 * 2，
 */
public interface ActiveMQConnectionFactoryImpl {
}
