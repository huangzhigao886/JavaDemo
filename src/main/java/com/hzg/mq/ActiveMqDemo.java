package com.hzg.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author: huangzhigao
 * @Date: 2019/12/10 22:44
 */
public class ActiveMqDemo {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String BROKERURL = "tcp://127.0.0.1:61616";
    private static final String QUEUENAME= "MyQueue";
    public static void start() throws JMSException {
        ActiveMQConnectionFactory activeMq = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
        Connection conn = activeMq.createConnection();
        //启动连接
        conn.start();
        //设置消息的可靠性，自动签收
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个队列
        Queue queue = session.createQueue(QUEUENAME);
        //创建生产者
        MessageProducer producer = session.createProducer(queue);
        //向队列发送数据
        TextMessage message = session.createTextMessage("hello world");
        producer.send(message);
        conn.close();
    }

    public static void main(String[] args) throws JMSException {
        start();
    }
}
