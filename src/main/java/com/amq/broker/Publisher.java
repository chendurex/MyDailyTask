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
import org.apache.qpid.jms.*;
import javax.jms.*;

class Publisher {

    public static void main(String[] args) throws Exception {
        int messages = 10;
        ConnectionFactory factory = new ActiveMQConnectionFactory(JmsComstant.URL);
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination;
        if (JmsComstant.isTopic) {
            destination = session.createTopic(JmsComstant.TOPIC);
        } else {
            destination = session.createQueue(JmsComstant.QUEUE);
        }
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.setDeliveryMode(JmsComstant.DELIVERY_MODE);
        System.out.println("send message start");
        for (int i = 1; i <= messages; i++) {
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setJMSType(JmsComstant.JMS_TYPE);
            objectMessage.setObject(new MessageObject("chendurex -- " + i, 20));
            producer.send(objectMessage);
            System.out.println(String.format("Sent %d messages", i));
        }
        Thread.sleep(1000 * 3);
        connection.close();
        System.exit(0);
    }

}