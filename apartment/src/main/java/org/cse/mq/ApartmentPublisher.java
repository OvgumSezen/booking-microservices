package org.cse.mq;

import lombok.RequiredArgsConstructor;
import org.cse.config.RabbitMQConfig;
import org.cse.model.Apartment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentPublisher {
    private final RabbitTemplate rabbitTemplate;

    private final String exchangeTopic = RabbitMQConfig.APP_EXCHANGE;

    public void publishApartmentCreated(Apartment apartment) {
        rabbitTemplate.convertAndSend(exchangeTopic, RabbitMQConfig.APARTMENT_CREATED_RK, apartment);
    }

    public void publishApartmentRemoved(Apartment apartment) {
        rabbitTemplate.convertAndSend(exchangeTopic, RabbitMQConfig.APARTMENT_REMOVED_RK, apartment.getId());
    }
}
