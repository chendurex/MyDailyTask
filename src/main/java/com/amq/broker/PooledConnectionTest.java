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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * A couple of tests against the PooledConnection class.
 *
 */
public class PooledConnectionTest extends JmsPoolTestSupport {

    private final Logger LOG = LoggerFactory.getLogger(PooledConnectionTest.class);

    /**
     * AMQ-3752:
     * Tests how the ActiveMQConnection reacts to repeated calls to
     * setClientID().
     *
     * @throws Exception
     */
    @Test(timeout = 60000)
    public void testRepeatedSetClientIDCalls() throws Exception {
        LOG.debug("running testRepeatedSetClientIDCalls()");

        // 1st test: call setClientID("newID") twice
        // this should be tolerated and not result in an exception
        //
        ConnectionFactory cf = createPooledConnectionFactory();
        Connection conn = cf.createConnection();
        conn.setClientID("newID");

        try {
            conn.setClientID("newID");
            conn.start();
            conn.close();
        } catch (IllegalStateException ise) {
            LOG.error("Repeated calls to ActiveMQConnection.setClientID(\"newID\") caused " + ise.getMessage());
            fail("Repeated calls to ActiveMQConnection.setClientID(\"newID\") caused " + ise.getMessage());
        } finally {
            ((PooledConnectionFactory) cf).stop();
        }

        // 2nd test: call setClientID() twice with different IDs
        // this should result in an IllegalStateException
        //
        cf = createPooledConnectionFactory();
        conn = cf.createConnection();
        conn.setClientID("newID1");
        try {
            conn.setClientID("newID2");
            fail("calling ActiveMQConnection.setClientID() twice with different clientID must raise an IllegalStateException");
        } catch (IllegalStateException ise) {
            LOG.error("Correctly received " + ise);
        } finally {
            conn.close();
            ((PooledConnectionFactory) cf).stop();
        }

        // 3rd test: try to call setClientID() after start()
        // should result in an exception
        cf = createPooledConnectionFactory();
        conn = cf.createConnection();
        try {
            conn.start();
            conn.setClientID("newID3");
            fail("Calling setClientID() after start() mut raise a JMSException.");
        } catch (IllegalStateException ise) {
            LOG.error("Correctly received " + ise);
        } finally {
            conn.close();
            ((PooledConnectionFactory) cf).stop();
        }

        LOG.debug("Test finished.");
    }

    @Test
    public void testPooledLifeCycle() throws Exception{
        ConnectionFactory factory = createPooledConnectionFactory();
        // create two connection pool
        Connection connection1 = factory.createConnection();
        Connection connection2 = factory.createConnection();

        // close connection pool ,can not use pool feature
        LOG.info("=-===========connection1:{}, connection2:{}", connection1 , connection2);
        connection1.close();
        LOG.info("=-===========connection1==={}======================", connection1);

        PooledConnection pooledConnection = (PooledConnection)factory.createConnection();
        PooledConnection pooledConnection2 = (PooledConnection)factory.createConnection();
        // although set maxConnections of per factory, but this is use for that the same connectionKey(username + password)
        // so , maxConnections real use for per connectionKey
        PooledConnection pooledConnection3 = (PooledConnection)factory.createConnection("1", "2");
        // use pool feature, thought close connection ,but still alive
        Connection connection4 = pooledConnection.getConnection();
        Connection connection5 = pooledConnection2.getConnection();
        Connection connection6 = pooledConnection3.getConnection();
        //assertSame(connection5, connection4);
        assertNotSame(connection4, connection5);
        assertNotSame(connection4, connection6);
        assertNotSame(connection5, connection6);
    }

    @Test(timeout = 60000)
    public void testClearAllConnections() throws Exception {

        ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory(
                "vm://broker1?marshal=false&broker.persistent=false&broker.useJmx=false");
        PooledConnectionFactory cf = new PooledConnectionFactory();
        cf.setConnectionFactory(amq);
        cf.setMaxConnections(3);

        PooledConnection conn1 = (PooledConnection) cf.createConnection();
        PooledConnection conn2 = (PooledConnection) cf.createConnection();
        PooledConnection conn3 = (PooledConnection) cf.createConnection();
        PooledConnection conn4 = (PooledConnection) cf.createConnection();

        assertNotSame(conn1.getConnection(), conn2.getConnection());
        assertNotSame(conn1.getConnection(), conn3.getConnection());
        assertNotSame(conn2.getConnection(), conn3.getConnection());
        assertSame(conn1.getConnection(), conn4.getConnection());

        assertEquals(3, cf.getNumConnections());

        cf.clear();

        assertEquals(0, cf.getNumConnections());

        conn1 = (PooledConnection) cf.createConnection();
        conn2 = (PooledConnection) cf.createConnection();
        conn3 = (PooledConnection) cf.createConnection();

        assertNotSame(conn1.getConnection(), conn2.getConnection());
        assertNotSame(conn1.getConnection(), conn3.getConnection());
        assertNotSame(conn2.getConnection(), conn3.getConnection());

        cf.stop();
    }

    @Test(timeout = 60000)
    public void testFactoryStopStart() throws Exception {

        ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory(
                "vm://broker1?marshal=false&broker.persistent=false&broker.useJmx=false");
        PooledConnectionFactory cf = new PooledConnectionFactory();
        cf.setConnectionFactory(amq);
        cf.setMaxConnections(1);

        PooledConnection conn1 = (PooledConnection) cf.createConnection();

        cf.stop();

        assertNull(cf.createConnection());

        cf.start();

        PooledConnection conn2 = (PooledConnection) cf.createConnection();

        assertNotSame(conn1.getConnection(), conn2.getConnection());

        assertEquals(1, cf.getNumConnections());

        cf.stop();
    }

    @Test(timeout = 60000)
    public void testConnectionsAreRotated() throws Exception {

        ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory(
                "vm://broker1?marshal=false&broker.persistent=false&broker.useJmx=false");
        PooledConnectionFactory cf = new PooledConnectionFactory();
        cf.setConnectionFactory(amq);
        cf.setMaxConnections(10);

        Connection previous = null;

        // Front load the pool.
        for (int i = 0; i < 10; ++i) {
            cf.createConnection();
        }

        for (int i = 0; i < 100; ++i) {
            Connection current = ((PooledConnection) cf.createConnection()).getConnection();
            assertNotSame(previous, current);
            previous = current;
        }

        cf.stop();
    }

    @Test(timeout = 60000)
    public void testConnectionsArePooledAsyncCreate() throws Exception {

        final ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory(
                "vm://broker1?marshal=false&broker.persistent=false&broker.useJmx=false");
        final PooledConnectionFactory cf = new PooledConnectionFactory();
        cf.setConnectionFactory(amq);
        cf.setMaxConnections(2);

        final ConcurrentLinkedQueue<PooledConnection> connections = new ConcurrentLinkedQueue<PooledConnection>();

        final PooledConnection primary = (PooledConnection) cf.createConnection();
        final ExecutorService executor = Executors.newFixedThreadPool(10);
        final int numConnections = 100;

        for (int i = 0; i < numConnections; ++i) {
            executor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        connections.add((PooledConnection) cf.createConnection());
                    } catch (JMSException e) {
                    }
                }
            });
        }

        executor.shutdown();
        assertTrue(executor.awaitTermination(25, TimeUnit.SECONDS));

        for (PooledConnection connection : connections) {
            assertSame(primary.getConnection(), connection.getConnection());
        }

        connections.clear();
        cf.stop();
    }

    protected ConnectionFactory createPooledConnectionFactory() {
        PooledConnectionFactory cf = new PooledConnectionFactory();
        cf.setConnectionFactory(new ActiveMQConnectionFactory(
                "vm://localhost?broker.persistent=false&broker.useJmx=false&broker.schedulerSupport=false"));
        cf.setMaxConnections(2);
        LOG.debug("ConnectionFactory initialized.");
        return cf;
    }
}
