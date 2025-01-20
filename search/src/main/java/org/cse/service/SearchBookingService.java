package org.cse.service;

import lombok.RequiredArgsConstructor;
import org.cse.model.Booking;
import org.cse.repository.SearchBookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchBookingService {
    private final SearchBookingRepository searchBookingRepository;

    public void createBooking(Booking booking) {
        searchBookingRepository.save(booking);
    }

    public void changeBooking(Booking booking) {
        Booking queriedBooking = searchBookingRepository.findBookingById(booking.getId());

        queriedBooking.setFrom(booking.getFrom());
        queriedBooking.setTo(booking.getTo());

        searchBookingRepository.save(queriedBooking);
    }

    public void cancelBooking(Integer bookingId) {
        searchBookingRepository.deleteById(bookingId);
    }
}
