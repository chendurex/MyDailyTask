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

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

class Listener extends Thread implements MessageListener {

    public static void main(String[] args) {
        new Listener().start();
    }

    @Override
    public void run() {
        try {
            final String TOPIC_PREFIX = "topic://";

            String user = env("ACTIVEMQ_USER", "admin");
            String password = env("ACTIVEMQ_PASSWORD", "password");
            String host = env("ACTIVEMQ_HOST", "localhost");
            int port = Integer.parseInt(env("ACTIVEMQ_PORT", "61616"));

            String connectionURI = "tcp://" + host + ":" + port;
            String destinationName = "topic://event";

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionURI);
            Connection connection = factory.createConnection();
            connection.setClientID(getClass().toString());
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination ;
            if (destinationName.startsWith(TOPIC_PREFIX)) {
                destination = session.createTopic(destinationName.substring(TOPIC_PREFIX.length()));
            } else {
                destination = session.createQueue(destinationName);
            }
            TopicSubscriber topicSubscription = session.createDurableSubscriber((Topic)destination, "test");

           // MessageConsumer consumer = session.createConsumer(destination);
            System.out.println("Waiting for messages...");
            topicSubscription.setMessageListener(this);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if (rc == null)
            return defaultValue;
        return rc;
    }

    private static String arg(String[] args, int index, String defaultValue) {
        if (index < args.length)
            return args[index];
        else
            return defaultValue;
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}