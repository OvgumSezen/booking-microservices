package org.cse.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
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

    public static final String APARTMENT_QUEUE = "apartmentQueueBooking";
    public static final String APP_EXCHANGE = "APP_EXCHANGE";

    public static final String APARTMENT_CREATED_RK = "apartment.created";
    public static final String APARTMENT_REMOVED_RK = "apartment.removed";

    public static final String BOOKING_CREATED_RK = "booking.created";
    public static final String BOOKING_CHANGED_RK = "booking.changed";
    public static final String BOOKING_CANCELLED_RK = "booking.cancelled";

    @Bean
    public Queue apartmentQueue() {
        return new Queue(APARTMENT_QUEUE, true);
    }

    @Bean
    public TopicExchange apartmentExchange() {
        return new TopicExchange(APP_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue apartmentQueue, TopicExchange apartmentExchange) {
        return BindingBuilder.bind(apartmentQueue).to(apartmentExchange).with("apartment.#");
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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
