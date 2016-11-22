package hello.message;

/**
 * Created by arabbani on 11/17/16.
 */

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver implements MessageListener{

    static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    @Autowired
    MessageConverter messageConverter;

    @Override
    public void onMessage(Message message) {
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            InventoryResponse response = (InventoryResponse) messageConverter.fromMessage(message);
            LOG.info("Application : order response received : {}",response);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}