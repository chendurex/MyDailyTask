package com.dayhr.jms;

import com.dayhr.config.DayhrUserConnectionConfig;

import javax.jms.JMSException;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:04
 */
public interface DayhrPooledConnectionFactory {
    DayhrConnection createConnection() throws JMSException;

    DayhrConnection createConnection(DayhrUserConnectionConfig userConfig) throws JMSException;

    DayhrQueueConnection createQueueConnection() throws JMSException;

    DayhrQueueConnection createQueueConnection(DayhrUserConnectionConfig userConfig) throws JMSException;

    DayhrTopicConnection createTopicConnection() throws JMSException;

    DayhrTopicConnection createTopicConnection(DayhrUserConnectionConfig userConfig) throws JMSException;
}
