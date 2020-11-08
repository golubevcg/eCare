package ecare.mq;

import ecare.controllers.CheckUserPageController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Component
public class MessageSender {

    static final Logger log = Logger.getLogger(CheckUserPageController.class);

    private static final String JMS_CONNECTION_FACTORY_JNDI = "jms/RemoteConnectionFactory";
    private static final String JMS_QUEUE_JNDI = "jms/quene/test";
    private static final String WILDFLY_REMOTING_URL = "http-remoting://127.0.0.1:8080";
    private static final String JMS_USERNAME = "quickstartUser";
    private static final String JMS_PASSWORD = "quickstartPwd1!";

    public void sendMessage(String message){

        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, WILDFLY_REMOTING_URL);
        props.put(Context.SECURITY_PRINCIPAL, JMS_USERNAME);
        props.put(Context.SECURITY_CREDENTIALS, JMS_PASSWORD);

        try {
            Context context = new InitialContext(props);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(JMS_CONNECTION_FACTORY_JNDI);
            Queue queue = (Queue) context.lookup(JMS_QUEUE_JNDI);
            QueueConnection connection = connectionFactory.createQueueConnection(JMS_USERNAME, JMS_PASSWORD);
            connection.start();
            QueueSession session = connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(queue);

            TextMessage textMessage = session.createTextMessage(message);

            sender.send(textMessage);

            session.close();
            connection.close();
        } catch (NamingException | JMSException e) {
            log.info("There was an error during message sending to mq.");
        }
    }

}
