package org.cse.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.cse.model.Booking;
import org.cse.repository.SearchBookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchBookingService {
    private final SearchBookingRepository searchBookingRepository;
    private final EntityManager entityManager;

    @Transactional
    public void createBooking(Booking booking) {
        booking.setId(null);
        searchBookingRepository.save(booking);
    }

    @Transactional
    public void changeBooking(Booking booking) {
        Booking queriedBooking = searchBookingRepository.findBookingById(booking.getId());

        queriedBooking.setFrom(booking.getFrom());
        queriedBooking.setTo(booking.getTo());

        searchBookingRepository.save(queriedBooking);
    }

    @Transactional
    public void cancelBooking(Integer bookingId) {
        searchBookingRepository.deleteById(bookingId);
    }
}
