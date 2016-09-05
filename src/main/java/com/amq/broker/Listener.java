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

import javax.jms.*;
import java.util.concurrent.TimeUnit;

class Listener extends Thread implements MessageListener {
    private static Session session;
    private static MessageProducer replyProducer;
    public static void main(String[] args) throws Exception{
        session = MQHelper.getSession();
        Thread t = new Listener();
        t.setDaemon(true);
        t.start();
        //TimeUnit.SECONDS.sleep(1000);
    }

    @Override
    public void run() {
        try {
            /*ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(JmsConstant.URL);
            factory.setTrustAllPackages(true);
            ActiveMQConnection connection = (ActiveMQConnection) factory.createConnection();
            connection.setClientID(getClass().toString());
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            */
            Destination destination ;
            if (JmsConstant.isTopic) {
                destination = session.createTopic(JmsConstant.TOPIC);
            } else {
                destination = session.createQueue(JmsConstant.QUEUE);
            }
            replyProducer = session.createProducer(null);
            replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            MessageConsumer consumer = session.createConsumer(destination, "JMSType = '"+ JmsConstant.JMS_TYPE+"'");

            System.out.println("Waiting for messages...");
            consumer.setMessageListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMessage(Message message) {

        try {
            if (message.getJMSReplyTo() != null) {
                replyProducer.send(message.getJMSReplyTo(), JMSReplyMsg(session,message));
            }
            else
            {
                //接收消息
                receiveMSg(message);

                //按照顺序发送下一个方法
                sendNextMsg(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }

    public Message JMSReplyMsg(Session session,Message message) throws JMSException {
        TextMessage response = session.createTextMessage();
        if (message instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) message;
            String messageText = txtMsg.getText();
            response.setText("Reply:收到消息  " +messageText + ":答案：2");
        }
        response.setJMSCorrelationID(message.getJMSCorrelationID());
        return response;

    }

    public void receiveMSg(Message message)
            throws JMSException
    {
        System.out.println("=-==================="+ message + "=======================");
    }

    private void sendNextMsg(Message message) throws Exception {
            //Session session = connection.createSession(false, ackMode);
            Session session = MQHelper.getSession();

            Destination destination = session.createQueue("sssssssssssssssssssss");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 发送消息
            producer.send(message);

        }
}