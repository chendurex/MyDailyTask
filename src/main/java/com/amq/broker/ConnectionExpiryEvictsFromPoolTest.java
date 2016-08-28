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
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ConnectionExpiryEvictsFromPoolTest extends JmsPoolTestSupport {

    private ActiveMQConnectionFactory factory;
    private PooledConnectionFactory pooledFactory;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        brokerService = new BrokerService();
        brokerService.setUseJmx(false);
        brokerService.setPersistent(false);
        brokerService.setSchedulerSupport(false);
        brokerService.setAdvisorySupport(false);
        TransportConnector connector = brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
        factory = new ActiveMQConnectionFactory("mock:" + connector.getConnectUri());
        pooledFactory = new PooledConnectionFactory();
        pooledFactory.setConnectionFactory(factory);
        pooledFactory.setMaxConnections(1);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        try {
            pooledFactory.stop();
        } catch (Exception ex) {
            // ignored
        }

        super.tearDown();
    }

    @Test(timeout = 60000)
    public void testEvictionOfIdle() throws Exception {
        pooledFactory.setIdleTimeout(100);
        PooledConnection connection = (PooledConnection) pooledFactory.createConnection();
        Connection amq1 = connection.getConnection();

        connection.close();

        // let it idle timeout
        TimeUnit.MILLISECONDS.sleep(50);

        PooledConnection connection2 = (PooledConnection) pooledFactory.createConnection();
        Connection amq2 = connection2.getConnection();
        connection2.close();

        // reuse can reset idleTimeOut
        TimeUnit.MILLISECONDS.sleep(60);
        PooledConnection connection3 = (PooledConnection) pooledFactory.createConnection();
        Connection amq3 = connection3.getConnection();
        assertTrue("is equal", amq1.equals(amq3));
    }

    @Test(timeout = 60000)
    public void testEvictionOfExpired() throws Exception {
        pooledFactory.setExpiryTimeout(100);
        pooledFactory.setIdleTimeout(60);
        Connection connection = pooledFactory.createConnection();
        Connection amq1 = ((PooledConnection) connection).getConnection();
        Session session = connection.createSession(true,1);
        MessageProducer producer = session.createProducer(session.createQueue(JmsConstant.QUEUE));
        ActiveMQObjectMessage objectMessage = (ActiveMQObjectMessage)session.createObjectMessage();
        objectMessage.setJMSType(JmsConstant.JMS_TYPE);
        objectMessage.setObject( new MessageObject("chendurex -- ", 20));
        producer.send(objectMessage);

        connection.close();
        // let it expire while in use
        TimeUnit.MILLISECONDS.sleep(50);


        Connection connection2 = pooledFactory.createConnection();
        Connection amq2 = ((PooledConnection) connection2).getConnection();
        assertTrue("is equal", amq1.equals(amq2));
        connection2.close();
        TimeUnit.MILLISECONDS.sleep(50);
        Connection connection3 = pooledFactory.createConnection();
        Connection amq3 = ((PooledConnection) connection3).getConnection();
        assertTrue("is equal", amq1.equals(amq3));
    }

    @Test(timeout = 60000)
    public void testNotIdledWhenInUse() throws Exception {
        pooledFactory.setExpiryTimeout(1000);
        PooledConnection connection = (PooledConnection) pooledFactory.createConnection();
        Session s = connection.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
       // connection.getConnection().close();
        // let connection to get idle
        TimeUnit.MILLISECONDS.sleep(500);

        // get a connection from pool again, it should be the same underlying connection
        // as before and should not be idled out since an open session exists.
        PooledConnection connection2 =  (PooledConnection)pooledFactory.createConnection();
        assertSame(connection.getConnection(), connection2.getConnection());

        // now the session is closed even when it should not be
        try {
            // any operation on session first checks whether session is closed
            s.getTransacted();
        } catch (javax.jms.IllegalStateException e) {
            assertTrue("Session should be fine, instead: " + e.getMessage(), false);
        }

    }
}
