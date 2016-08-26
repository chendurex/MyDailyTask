package com.amq.broker;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.activemq.jms.pool.PooledProducer;
import org.apache.activemq.jms.pool.PooledTopicPublisher;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * @author chen
 * @description
 * @pachage com.amq.broker
 * @date 2016/8/25 11:33
 */
public class ConnectionSessionTest {
    private static  Logger LOG = LoggerFactory.getLogger(ConnectionSessionTest.class);
    @Test(timeout = 60000)
    public void testCreateSessionDoesNotBlockWhenNotConfiguredTo() throws Exception {
        // using separate thread for testing so that we can interrupt the test
        // if the call to get a new session blocks.

        // start test runner thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Boolean> result = executor.submit(new TestRunner());
        if (result.get()) {
            LOG.error("2nd call to createSession() " +
                    "is blocking but should have returned an error instead.");
            executor.shutdownNow();
            fail("SessionPool inside PooledConnectionFactory is blocking if " +
                    "limit is exceeded but should return an exception instead.");
        }
    }

    static class TestRunner implements Callable<Boolean> {

        public final static Logger LOG = LoggerFactory.getLogger(TestRunner.class);

        /**
         * @return true if test succeeded, false otherwise
         */
        @Override
        public Boolean call() {

            Connection conn = null;
            Session one = null;

            PooledConnectionFactory cf = null;

            // wait at most 5 seconds for the call to createSession
            try {
                ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory(
                        "vm://broker1?marshal=false&broker.persistent=false&broker.useJmx=false");
                cf = new PooledConnectionFactory();
                cf.setConnectionFactory(amq);
                cf.setMaxConnections(3);
                cf.setMaximumActiveSessionPerConnection(1);
                cf.setBlockIfSessionPoolIsFull(false);

                conn = cf.createConnection();
                one = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Session two = null;
                try {
                    // this should raise an exception as we called setMaximumActive(1)
                    two = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    two.close();

                    LOG.error("Expected JMSException wasn't thrown.");
                    fail("seconds call to Connection.createSession() was supposed" +
                            "to raise an JMSException as internal session pool" +
                            "is exhausted. This did not happen and indiates a problem");
                    return new Boolean(false);
                } catch (JMSException ex) {
                    if (ex.getCause().getClass() == java.util.NoSuchElementException.class) {
                        // expected, ignore but log
                        LOG.info("Caught expected " + ex);
                    } else {
                        LOG.error("", ex);
                        return new Boolean(false);
                    }
                } finally {
                    if (one != null) {
                        one.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                }
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                return new Boolean(false);
            } finally {
                if (cf != null) {
                    cf.stop();
                }
            }

            // all good, test succeeded
            return new Boolean(true);
        }
    }


    @Test(timeout = 60000)
    public void testPooledSessionStats() throws Exception {
        PooledConnection connection = (PooledConnection) createPooledConnectionFactory().createConnection();

        assertEquals(0, connection.getNumActiveSessions());
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        assertEquals(1, connection.getNumActiveSessions());
        session.close();
        assertEquals(0, connection.getNumActiveSessions());
        assertEquals(1, connection.getNumtIdleSessions());
        assertEquals(1, connection.getNumSessions());

        connection.close();
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
