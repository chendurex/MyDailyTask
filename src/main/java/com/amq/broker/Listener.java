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
import org.apache.activemq.broker.BrokerRegistry;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class Listener extends Thread {
    protected BrokerService brokerService;
    protected ActiveMQConnectionFactory factory;
    protected Connection producerConnection;
    protected Connection consumerConnection;
    protected Session consumerSession;
    protected Session producerSession;
    protected MessageConsumer consumer;
    protected MessageProducer producer;
    protected Topic topic;
    protected int messageCount = 10;
    protected int timeOutInSeconds = 10;

    public static void main(String[] args) {
        new Listener().start();
    }


    protected void setUp() throws Exception {
        /*brokerService = new BrokerService();
        brokerService.setPersistent(false);
        brokerService.start();*/

        factory =  new ActiveMQConnectionFactory("tcp://localhost:61616");
        consumerConnection = factory.createConnection();
        consumerConnection.start();
       // producerConnection = factory.createConnection();
      //  producerConnection.start();
        consumerSession = consumerConnection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        topic = consumerSession.createTopic("event");
       // producerSession = producerConnection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        consumer = consumerSession.createConsumer(topic);
       // producer = producerSession.createProducer(topic);
    }

    //@Override
    public void run1() {

        final CountDownLatch latch = new CountDownLatch(messageCount);

        try {
            setUp();
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    latch.countDown();
                    System.out.println("enter");
                }
            });

        latch.await(timeOutInSeconds, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            /*Destination destination ;
            if (destinationName.startsWith(TOPIC_PREFIX)) {
                destination = session.createTopic(destinationName.substring(TOPIC_PREFIX.length()));
            } else {
                destination = session.createQueue(destinationName);
            }*/
            Topic topic = session.createTopic(destinationName.substring(TOPIC_PREFIX.length()));

            MessageConsumer consumer = session.createConsumer(topic);


            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println("enter");
                }
            });
            long start = System.currentTimeMillis();
            long count = 1;
            System.out.println("Waiting for messages...");
            /*while (true) {
                Message msg = consumer.receive();
                if (msg instanceof TextMessage) {
                    String body = ((TextMessage) msg).getText();
                    if ("SHUTDOWN".equals(body)) {
                        long diff = System.currentTimeMillis() - start;
                        System.out.println(String.format("Received %d in %.2f seconds", count, (1.0 * diff / 1000.0)));
                        //connection.close();
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {}
                        System.exit(1);
                    } else {
                        try {
                            if (count != msg.getIntProperty("id")) {
                                System.out.println("mismatch: " + count + "!=" + msg.getIntProperty("id"));
                            }
                        } catch (NumberFormatException ignore) {
                        }

                        if (count == 1) {
                            start = System.currentTimeMillis();
                        } else if (count % 1000 == 0) {
                            System.out.println(String.format("Received %d messages.", count));
                        }
                        count++;
                    }

                } else {
                    System.out.println("Unexpected message type: " + msg.getClass());
                }
            }*/
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


}