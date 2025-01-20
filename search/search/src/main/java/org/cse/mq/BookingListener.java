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
    private final SearchBookingService bookingService;

    @RabbitListener(queues = RabbitMQConfig.BOOKING_QUEUE)
    public void handleBookingMessages(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();

        if(routingKey.equals(RabbitMQConfig.BOOKING_CREATED_RK)) {
            System.out.println("message received: " + message);

            Booking booking = handleBookingCreated(message);
            bookingService.createBooking(booking);

            System.out.println("booking created: " + booking);
        }

        if(routingKey.equals(RabbitMQConfig.BOOKING_CHANGED_RK)) {
            System.out.println("message received: " + message);

            Booking booking = handleBookingChanged(message);
            bookingService.changeBooking(booking);

            System.out.println("booking changed: " + booking);
        }

        if(routingKey.equals(RabbitMQConfig.BOOKING_CANCELLED_RK)) {
            System.out.println("message received: " + message);

            Integer bookingId = handleBookingCancelled(message);
            bookingService.cancelBooking(bookingId);

            System.out.println("booking cancelled:" + bookingId);
        }
    }

    private Booking handleBookingCreated(Message message) {
        try {
            return objectMapper.readValue(message.getBody(), Booking.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Booking handleBookingChanged(Message message) {
        try {
            return objectMapper.readValue(message.getBody(), Booking.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer handleBookingCancelled(Message message) {
        String messageBody = new String(message.getBody());
        return Integer.parseInt(messageBody);
    }
}
