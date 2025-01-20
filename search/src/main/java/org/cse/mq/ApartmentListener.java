package org.cse.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cse.config.RabbitMQConfig;
import org.cse.model.Apartment;
import org.cse.service.SearchApartmentService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ApartmentListener {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SearchApartmentService apartmentService;

    @RabbitListener(queues = RabbitMQConfig.APARTMENT_QUEUE)
    public void handleApartmentMessages(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();

        if(routingKey.equals(RabbitMQConfig.APARTMENT_CREATED_RK)) {
            handleApartmentCreated(message);
        }

        if(routingKey.equals(RabbitMQConfig.APARTMENT_REMOVED_RK)) {
            handleApartmentRemoved(message);
        }
    }

    private void handleApartmentCreated(Message message) {
        try {
            System.out.println("message received: " + message);

            Apartment apartment = objectMapper.readValue(message.getBody(), Apartment.class);
            apartmentService.createApartment(apartment);

            System.out.println("apartment created: " + apartment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleApartmentRemoved(Message message) {
        System.out.println("message received: " + message);

        Integer apartmentId = Integer.parseInt(new String(message.getBody()));
        apartmentService.removeApartment(apartmentId);

        System.out.println("apartment removed: " + apartmentId);
    }
}
