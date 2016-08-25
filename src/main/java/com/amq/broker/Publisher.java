/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amq.broker;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ProducerId;

import javax.jms.*;

class Publisher {

    public static void main(String[] args) throws Exception {
        int messages = 10;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(JmsConstant.URL);
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination;
        if (JmsConstant.isTopic) {
            destination = session.createTopic(JmsConstant.TOPIC);
        } else {
            destination = session.createQueue(JmsConstant.QUEUE);
        }
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.setDeliveryMode(JmsConstant.DELIVERY_MODE);
        System.out.println("send message start");
        for (int i = 1; i <= messages; i++) {
            ActiveMQObjectMessage objectMessage = (ActiveMQObjectMessage)session.createObjectMessage();
            objectMessage.setJMSType(JmsConstant.JMS_TYPE);
            objectMessage.setObject( new MessageObject("chendurex -- ", 20));
            producer.send(objectMessage);
            objectMessage.setProducerId(new ProducerId("111"));
            System.out.println(String.format("Sent %d messages", i));
        }
        Thread.sleep(1000 * 3);
        connection.close();
        System.exit(0);
    }

}