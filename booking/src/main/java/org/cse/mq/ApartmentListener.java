package org.cse.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.cse.config.RabbitMQConfig;
import org.cse.model.Apartment;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ApartmentListener {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Integer> apartments = new CopyOnWriteArrayList<>();

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

            System.out.println("apartment received: " + apartment.toString());

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

    public void clearApartments() {
        apartments.clear();
    }

    public boolean containsApartment(Integer apartment) {
        return apartments.contains(apartment);
    }

    public void addApartments(List<Integer> apartments) {
        this.apartments.addAll(apartments);
    }
}
