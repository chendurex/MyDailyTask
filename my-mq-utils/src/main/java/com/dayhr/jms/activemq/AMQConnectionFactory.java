package com.dayhr.jms.activemq;

import com.dayhr.config.DayhrConnectionConfig;
import com.dayhr.jms.DayhrConnection;
import com.dayhr.jms.DayhrConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author chen
 * @description
 * @pachage com.dayhr.jms
 * @date 2016/8/31 11:04
 */
public class AMQConnectionFactory implements DayhrConnectionFactory {
    private final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

    @Override
    public DayhrConnection createConnection() {

        return null;
    }

    @Override
    public DayhrConnection createConnection(DayhrConnectionConfig config) {
        return null;
    }
}
