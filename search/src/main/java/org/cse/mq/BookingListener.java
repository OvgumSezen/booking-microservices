package org.cse.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.cse.config.RabbitMQConfig;
import org.cse.model.Booking;
import org.cse.service.SearchBookingService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BookingListener {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final SearchBookingService searchBookingService;

    @RabbitListener(queues = RabbitMQConfig.BOOKING_QUEUE)
    public void handleBookingMessages(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();

        if(routingKey.equals(RabbitMQConfig.BOOKING_CREATED_RK)) {
            handleBookingCreated(message);
        }

        if(routingKey.equals(RabbitMQConfig.BOOKING_CHANGED_RK)) {
            handleBookingChanged(message);
        }

        if(routingKey.equals(RabbitMQConfig.BOOKING_CANCELLED_RK)) {
            handleBookingCancelled(message);
        }
    }

    private void handleBookingCreated(Message message) {
        try {
            System.out.println("message received: " + message);

            Booking booking = objectMapper.readValue(message.getBody(), Booking.class);
            searchBookingService.createBooking(booking);

            System.out.println("booking created: " + booking);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleBookingChanged(Message message) {
        try {
            System.out.println("message received: " + message);

            Booking booking = objectMapper.readValue(message.getBody(), Booking.class);
            searchBookingService.changeBooking(booking);

            System.out.println("booking changed: " + booking);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleBookingCancelled(Message message) {
        System.out.println("message received: " + message);

        String messageBody = new String(message.getBody());
        Integer bookingId = Integer.parseInt(messageBody);
        searchBookingService.cancelBooking(bookingId);

        System.out.println("booking cancelled:" + bookingId);
    }
}
