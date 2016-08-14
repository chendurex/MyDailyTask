
package com.amq.broker;

/**
 * Copyright dayhr2014-2015 All rights reserved
 * @ClassName: MQProductHelper
 * @Description: mq连接池
 * @author ganyunjing
 * @date 2015-12-2 下午9:08:40
 * Update Logs:
 * ****************************************************
 * @author:
 * @date:
 * Description:
 * @version
 * *****************************************************
 */


import java.util.HashMap;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MQProductHelper {
    public static final Logger LOG = LoggerFactory.getLogger(MQProductHelper.class);

    private static Map map = new HashMap();

    /**
     * 获取单例的PooledConnectionFactory
     *  @return
     * @throws JMSException
     */
    private static synchronized PooledConnection getPooledConnectionFactory(String userName, String password,String brokerURL) throws JMSException {
        String key = userName+password+brokerURL;
        PooledConnection pooledConnection ;
        if (map.get(key) == null)
        {
            LOG.info("getPooledConnectionFactory create new");
            PooledConnectionFactory poolFactory;

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
            poolFactory = new PooledConnectionFactory(factory);
            // 池中借出的对象的最大数目
            poolFactory.setMaxConnections(1);
            poolFactory.setMaximumActiveSessionPerConnection(10000);
            //后台对象清理时，休眠时间超过了3000毫秒的对象为过期
            //poolFactory.setTimeBetweenExpirationCheckMillis(3000);
            LOG.info("getPooledConnectionFactory create success");

            pooledConnection = (PooledConnection) poolFactory.createConnection();
            pooledConnection.setClientID(String.valueOf(Math.random()));
            pooledConnection.start();

            map.put(key, pooledConnection);
        }
        else
        {
            pooledConnection = (PooledConnection) map.get(key);
        }
        return pooledConnection;
    }

    /**
     * 1.对象池管理connection和session,包括创建和关闭等
     * 2.PooledConnectionFactory缺省设置MaxIdle为1，
     *  官方解释Set max idle (not max active) since our connections always idle in the pool.   *
     *  @return   * @throws JMSException
     */
    public static Session createSession(String userName, String password,String brokerURL,boolean transacted) throws JMSException {
        PooledConnection pooledConnection = getPooledConnectionFactory(userName,password,brokerURL);
        //false 参数表示 为非事务型消息，后面的参数表示消息的确认类型（见4.消息发出去后的确认模式）
        return pooledConnection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);

    }

    public static void produce(String subject, String msg) {
        LOG.info("producer send msg: {} ", msg);
        if (msg == null || msg.equals("")) {
            LOG.warn("发送消息不能为空。");
            return;
        }
        try {
            Session session = createSession(null,null,null,false);
            LOG.info("create session: "+session);
            TextMessage textMessage = session.createTextMessage(msg);
            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(textMessage);
            if(session != null)
                session.close();
            LOG.info("create session success");
        } catch (JMSException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        long aa = System.currentTimeMillis();
        for(int i = 0 ; i < 100000 ; i++ )
        {
            MQProductHelper.produce("test.subject", "hello");
        }
        long bb = System.currentTimeMillis();
        System.out.println("时间： "+(bb -aa));
    }
}

