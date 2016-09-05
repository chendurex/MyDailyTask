package com.amq.broker;

import javax.jms.DeliveryMode;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface JmsConstant {
    String URL = "failover:(tcp://localhost:61616)?initialReconnectDelay=100";
    String VM_URL = "vm://broker1?marshal=false&broker.useJmx=false&broker.persistent=false";
    String BROKER_URL = "broker:tcp://localhost:61616";
    String BROKER_NAME = "MY_BROKER";
    String TOPIC = "topic://event";
    String QUEUE = "queue://event";
    int DELIVERY_MODE = DeliveryMode.NON_PERSISTENT;
    String JMS_TYPE = "TEST";
    boolean isTopic = true;
}
