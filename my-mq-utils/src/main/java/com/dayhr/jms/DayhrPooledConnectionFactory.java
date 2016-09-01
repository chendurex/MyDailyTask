package com.dayhr.jms;

import javax.jms.JMSException;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:04
 */
public interface DayhrPooledConnectionFactory {
    DayhrConnection createConnection() throws JMSException;

    DayhrQueueConnection createQueueConnection() throws JMSException;

    DayhrTopicConnection createTopicConnection() throws JMSException;
}
