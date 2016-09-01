package com.dayhr.jms.activemq;

import com.dayhr.jms.DayhrConnection;
import com.dayhr.jms.DayhrQueueConnection;
import com.dayhr.jms.DayhrTopicConnection;
import org.apache.activemq.ActiveMQConnection;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:05
 */
public class AMQConnection implements DayhrConnection, DayhrTopicConnection, DayhrQueueConnection {
    private ActiveMQConnection connection;
    public AMQConnection(ActiveMQConnection connection) {
        this.connection = connection;
    }
}
