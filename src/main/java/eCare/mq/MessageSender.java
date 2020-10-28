package eCare.mq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.Set;

@Component
public class MessageSender {

    final
    JmsTemplate jmsTemplate;

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String string)
    {

        jmsTemplate.send(new MessageCreator()
        {
            public Message createMessage(Session session) throws JMSException
            {
                ObjectMessage objectMessage = session.createObjectMessage(string);
                return objectMessage;
            }
        });
    }

    public void sendTariffsDTOSet(Set<TariffDTO> tariffDTOSet){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.sendMessage(gson.toJson(tariffDTOSet));
    }

}
