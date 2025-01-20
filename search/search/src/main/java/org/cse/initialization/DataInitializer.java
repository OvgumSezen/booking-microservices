package org.cse.initialization;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.cse.client.ApartmentClient;
import org.cse.client.BookingClient;
import org.cse.model.Apartment;
import org.cse.model.Booking;
import org.cse.repository.SearchApartmentRepository;
import org.cse.repository.SearchBookingRepository;
import org.cse.response.ApartmentResponse;
import org.cse.response.BookingResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final SearchBookingRepository searchBookingRepository;
    private final SearchApartmentRepository searchApartmentRepository;

    private final BookingClient bookingClient;
    private final ApartmentClient apartmentClient;

    @PostConstruct
    @Transactional
    public void initialize() {
        emptyTables();

        List<ApartmentResponse> apartmentResponses = apartmentClient.getApartments();
        List<BookingResponse> bookingResponses = bookingClient.getBookings();

        List<Apartment> apartments = mapApartmentResponsesToApartments(apartmentResponses);
        List<Booking> bookings = mapBookingResponsesToBookings(bookingResponses);

        saveNewData(apartments, bookings);
    }

    protected void emptyTables() {
        searchBookingRepository.deleteAll();
        searchApartmentRepository.deleteAll();
    }

    protected void saveNewData(List<Apartment> apartments, List<Booking> bookings) {
        searchApartmentRepository.saveAll(apartments);
        searchBookingRepository.saveAll(bookings);
    }

    private List<Apartment> mapApartmentResponsesToApartments(List<ApartmentResponse> apartmentResponses) {
        return apartmentResponses
                .stream()
                .map(apartmentResponse -> Apartment.builder()
                        .id(null)
                        .name(apartmentResponse.getName())
                        .address(apartmentResponse.getAddress())
                        .floor(apartmentResponse.getFloor())
                        .noiseLevel(apartmentResponse.getNoiseLevel())
                        .build())
                .toList();
    }

    private List<Booking> mapBookingResponsesToBookings(List<BookingResponse> bookingResponses) {
        return bookingResponses
                .stream()
                .map(bookingResponse -> Booking.builder()
                        .id(null)
                        .apartmentId(bookingResponse.getApartmentId())
                        .from(bookingResponse.getFrom())
                        .to(bookingResponse.getTo())
                        .who(bookingResponse.getWho())
                        .build())
                .toList();
    }
}
