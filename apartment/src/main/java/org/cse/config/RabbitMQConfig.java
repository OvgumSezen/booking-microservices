package org.cse.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {
    private final Environment env;

    public static final String APP_EXCHANGE = "APP_EXCHANGE";

    public static final String APARTMENT_CREATED_RK = "apartment.created";
    public static final String APARTMENT_REMOVED_RK = "apartment.removed";

    @Bean
    public TopicExchange apartmentExchange() {
        return new TopicExchange(APP_EXCHANGE);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername(Objects.requireNonNull(env.getProperty("spring.rabbitmq.username")));
        connectionFactory.setPassword(Objects.requireNonNull(env.getProperty("spring.rabbitmq.password")));

        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
