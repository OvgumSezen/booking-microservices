package org.cse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cse.model.Booking;
import org.cse.mq.ApartmentListener;
import org.cse.mq.BookingPublisher;
import org.cse.repository.BookingRepository;
import org.cse.response.BookingResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingPublisher bookingPublisher;
    private final BookingRepository bookingRepository;

    @Transactional
    public BookingResponse add(Integer apartment, LocalDateTime from, LocalDateTime to, String who) {
        if(!ApartmentListener.apartments.contains(apartment)) {
            return null;
        }

        Booking booking = Booking.builder()
                .apartmentId(apartment)
                .from(from)
                .to(to)
                .who(who)
                .build();

        booking = bookingRepository.save(booking);

        bookingPublisher.publishBookingCreated(booking);

        return mapBookingToResponse(booking);
    }

    @Transactional
    public BookingResponse cancel(Integer id) {
        Booking booking = bookingRepository.findBookingById(id);
        bookingRepository.deleteById(booking.getId());

        bookingPublisher.publishBookingCancelled(booking);

        return mapBookingToResponse(booking);
    }

    @Transactional
    public BookingResponse change(Integer id, LocalDateTime from, LocalDateTime to) {
        Booking booking = bookingRepository.findBookingById(id);

        booking.setFrom(from);
        booking.setTo(to);

        bookingRepository.save(booking);

        bookingPublisher.publishBookingChanged(booking);

        return mapBookingToResponse(booking);
    }


    public List<BookingResponse> list() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(BookingService::mapBookingToResponse)
                    .toList();
    }

    private static BookingResponse mapBookingToResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .apartmentId(booking.getApartmentId())
                .from(booking.getFrom())
                .to(booking.getTo())
                .who(booking.getWho())
                .build();
    }
}
