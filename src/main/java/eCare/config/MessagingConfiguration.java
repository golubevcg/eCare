package eCare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import java.util.Arrays;

@Configuration
public class MessagingConfiguration {

    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
    private static final String MESSAGE_TOPIC = "message_topic";

    @Bean
    public ConnectionFactory connectionFactory()
    {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
        connectionFactory.setTrustedPackages(Arrays.asList("eCare"));
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate()
    {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(MESSAGE_TOPIC);
        template.setPubSubDomain(true);
        return template;
    }

    @Bean
    MessageConverter converter()
    {
        return new SimpleMessageConverter();
    }

}
