package org.cse.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cse.config.RabbitMQConfig;
import org.cse.model.Apartment;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApartmentListener {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public static List<Integer> apartments = new ArrayList<>();

    @RabbitListener(queues = RabbitMQConfig.APARTMENT_QUEUE)
    public void handleApartmentMessages(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();

        if(routingKey.equals(RabbitMQConfig.APARTMENT_CREATED_RK)) {
            if(message.getBody() == null) {
                return;
            }

            handleApartmentCreated(message);
        }

        if (routingKey.equals(RabbitMQConfig.APARTMENT_REMOVED_RK)) {
            if(message.getBody() == null) {
                return;
            }

            handleApartmentRemoved(message);
        }
    }

    private void handleApartmentCreated(Message message) {
        try {
            System.out.println("message received: " + message);

            Apartment apartment = objectMapper.readValue(message.getBody(), Apartment.class);

            System.out.println("apartment received: " + apartment.toString() + "with id: " + apartment.getId());

            if(!apartments.contains(apartment.getId())) {
                apartments.add(apartment.getId());
            }

            System.out.println("updated apartments list: " + apartments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleApartmentRemoved(Message message) {
        System.out.println("message received: " + message);

        String messageBody = new String(message.getBody());
        apartments.removeIf(apartmentId -> apartmentId.equals(Integer.parseInt(messageBody)));

        System.out.println("updated apartments list: " + apartments);
    }
}
