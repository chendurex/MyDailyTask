/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amq.broker;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

class Listener extends Thread implements MessageListener {
    private static Session session;
    public static void main(String[] args) throws Exception{
        session = MQHelper.getSession();
        new Listener().start();
        TimeUnit.SECONDS.sleep(1000);
    }

    @Override
    public void run() {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(JmsConstant.URL);
            factory.setTrustAllPackages(true);
            ActiveMQConnection connection = (ActiveMQConnection) factory.createConnection();
            connection.setClientID(getClass().toString());
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination ;
            if (JmsConstant.isTopic) {
                destination = session.createTopic(JmsConstant.TOPIC);
            } else {
                destination = session.createQueue(JmsConstant.QUEUE);
            }

            MessageConsumer consumer = session.createConsumer(destination, "JMSType = '"+ JmsConstant.JMS_TYPE+"'");
            System.out.println("Waiting for messages...");
            consumer.setMessageListener(this);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMessage(Message message) {
        System.out.println(message);
    }

}