package com.amq.broker;

import javax.jms.DeliveryMode;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface JmsComstant {
    String URL = "failover:(tcp://localhost:61616)?maxReconnectAttempts=-1";
    String BROKER_URL = "broker:tcp://localhost:61616";
    String BROKER_NAME = "MY_BROKER";
    String TOPIC = "topic://event";
    String QUEUE = "queue://event";
    int DELIVERY_MODE = DeliveryMode.NON_PERSISTENT;
    String JMS_TYPE = "TEST";
    boolean isTopic = true;
}
