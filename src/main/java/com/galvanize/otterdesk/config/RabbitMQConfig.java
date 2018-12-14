package com.galvanize.otterdesk.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Value("${LOCALAMQP_URL}")
    String amqpUri;
    @Value("${amqp.exchange.name}")
    String appExchangeName;
    @Value("${amqp.message_bus.binding.key}")
    String messageBusBinding;
    @Value("${amqp.message_bus.queue}")
    String messageBusQueue;

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        LOGGER.info("Create RabbitMQ Template Bean with own connection factory");
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        LOGGER.info("Create producerJackson2MessageConverter bean");
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public FanoutExchange getAppExchange() {
        return new FanoutExchange(appExchangeName);
    }

    @Bean
    public Queue messageBusQueue() {
        return new Queue(messageBusQueue);
    }

    @Bean
    public Binding messageBusBinding() {
        return BindingBuilder.bind(messageBusQueue()).to(getAppExchange());
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

//    @Bean
//    ConnectionFactory connectionFactory(String amqpUri) {
//        LOGGER.info("Create Connection factory Bean with owm AMQP URI: {}", amqpUri);
//        final CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setUri(amqpUri);
//        return factory;
//    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }


//    @Bean
//    ConnectionFactory connectionFactory(String amqpUri) {
//        LOGGER.info("Create Connection factory Bean with owm AMQP URI: {}", amqpUri);
//        final CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setUri(amqpUri);
//        return factory;
//    }


}
