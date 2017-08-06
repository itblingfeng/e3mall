import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import javax.swing.*;

import java.io.IOException;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;
public class Test {
    @org.junit.Test
    public void TestQueueProducer() throws JMSException {
        /*创建连接工厂*/
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.116:61616");
        /*创建一个连接*/
        Connection connection = connectionFactory.createConnection();
        /*开启连接*/
        connection.start();
        /*不开启事务,自动会话*/
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        /*创建q*/
        Queue queue = session.createQueue("testQueue");
        /*创建生产者*/
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("我勒个去啊啊啊啊啊");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();

    }
    @org.junit.Test
    public void TestQueueConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.116:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("testQueue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.print(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();

    }
    @org.junit.Test
    public void TestQueue(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/application-mq.xml");
        JmsTemplate template = applicationContext.getBean(JmsTemplate.class);
        Destination destination = (Destination) applicationContext.getBean("queueDestination");
        template.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("hello，nice to meet you1");
                return textMessage;
            }
        });

    }

}
