package org.cse.mq;

import lombok.RequiredArgsConstructor;
import org.cse.config.RabbitMQConfig;
import org.cse.model.Booking;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingPublisher {
    private final RabbitTemplate rabbitTemplate;

    private final String exchangeTopic = RabbitMQConfig.APP_EXCHANGE;

    public void publishBookingCreated(Booking booking) {
        rabbitTemplate.convertAndSend(exchangeTopic, RabbitMQConfig.BOOKING_CREATED_RK, booking);
    }

    public void publishBookingChanged(Booking booking) {
        rabbitTemplate.convertAndSend(exchangeTopic, RabbitMQConfig.BOOKING_CHANGED_RK, booking);
    }

    public void publishBookingCancelled(Booking booking) {
        rabbitTemplate.convertAndSend(exchangeTopic, RabbitMQConfig.BOOKING_CANCELLED_RK, booking.getId());
    }
}
