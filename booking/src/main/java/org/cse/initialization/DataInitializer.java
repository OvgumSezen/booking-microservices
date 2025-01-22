package org.cse.initialization;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.cse.client.ApartmentClient;
import org.cse.mq.ApartmentListener;
import org.cse.response.ApartmentResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final ApartmentClient apartmentClient;
    private final ApartmentListener apartmentListener;

    @PostConstruct
    @Transactional
    public void initialize() {
        apartmentListener.clearApartments();
        List<ApartmentResponse> apartmentResponses = apartmentClient.getAllApartments();
        apartmentListener.addApartments(apartmentResponses.stream().map(ApartmentResponse::getId).toList());
    }
}
