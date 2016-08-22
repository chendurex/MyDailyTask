package com.amq.broker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;

/**
 * @author chen
 * @description
 * @pachage com.amq.broker
 * @date 2016/8/11 21:09
 */
public class MqBroker {

    public static void main(String[] args) throws Exception {
       // BrokerService brokerService = new BrokerService();
         BrokerService brokerService = BrokerFactory.createBroker(new URI("broker:tcp://localhost:61616"));
        // BrokerService broker = BrokerFactory.createBroker(new URI("xbean:com/test/activemq.xml"));
       /* in spring
       <bean id="broker" class="org.apache.activemq.xbean.BrokerFactor
        yBean">
         <property name="config" value="classpath:org/apache/activemq/
        xbean/activemq.xml" />
         <property name="start" value="true" />
          </bean>*/
        brokerService.setBrokerName("myBroker");
       // brokerService.addConnector("tcp://localhost:61616");
        System.out.println("==================broker start==================");
        brokerService.start();

    }
}
