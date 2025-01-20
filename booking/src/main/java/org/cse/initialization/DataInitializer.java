package org.cse.initialization;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.cse.client.ApartmentClient;
import org.cse.mq.ApartmentListener;
import org.cse.repository.BookingRepository;
import org.cse.response.ApartmentResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final BookingRepository bookingRepository;
    private final ApartmentClient apartmentClient;

    @PostConstruct
    @Transactional
    public void initialize() {
        bookingRepository.removeBookings();

        List<ApartmentResponse> apartmentResponses = apartmentClient.getAllApartments();
        ApartmentListener.apartments = apartmentResponses.stream().map(ApartmentResponse::getId).toList();
    }
}
