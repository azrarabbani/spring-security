package hello;


import java.util.Arrays;

import javax.jms.ConnectionFactory;

import hello.data.config.MessageConfigurationProperties;
import hello.message.MessageReceiver;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

/**
 * Created by arabbani on 11/17/16.
 */


@Configuration
public class MessagingConfiguration {

    private static final String ORDER_QUEUE = "order-queue";
    private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";


    @Autowired
    MessageConfigurationProperties messageConfigurationProperties;

    @Autowired
    MessageReceiver messageReceiver;


    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(messageConfigurationProperties.getDefaultBrokerUrl());
        //connectionFactory.setTrustedPackages(Arrays.asList("hello"));
        return connectionFactory;
    }
    /*
     * Optionally you can use cached connection factory if performance is a big concern.
     */

    @Bean
    public ConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(connectionFactory());
        connectionFactory.setSessionCacheSize(10);
        return connectionFactory;
    }

    /*
     * Message listener container, used for invoking messageReceiver.onMessage on message reception.
     */
    @Bean
    public MessageListenerContainer getContainer(){
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName(ORDER_RESPONSE_QUEUE);
        container.setMessageListener(messageReceiver);
        return container;
    }

    /*
     * Used for Sending Messages.
     */
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(ORDER_QUEUE);
        return template;
    }


    @Bean
    MessageConverter converter(){
        return new SimpleMessageConverter();
    }

}
}
