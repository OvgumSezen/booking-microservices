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
            try {
                System.out.println("message received: " + message);

                Apartment apartment = objectMapper.readValue(message.getBody(), Apartment.class);
                apartments.add(apartment.getId());

                System.out.println("updated apartments list: " + apartments);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (routingKey.equals(RabbitMQConfig.APARTMENT_REMOVED_RK)) {
            String messageBody = new String(message.getBody());
            apartments.removeIf(apartmentId -> apartmentId.equals(Integer.parseInt(messageBody)));
            System.out.println(apartments);
        }
    }
}
