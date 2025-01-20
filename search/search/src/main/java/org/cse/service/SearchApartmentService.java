package org.cse.service;

import lombok.RequiredArgsConstructor;
import org.cse.model.Apartment;
import org.cse.repository.SearchApartmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchApartmentService {
    private final SearchApartmentRepository searchApartmentRepository;

    public void createApartment(Apartment apartment) {
        searchApartmentRepository.save(apartment);
    }

    public void removeApartment(Integer apartmentId) {
        searchApartmentRepository.deleteById(apartmentId);
    }
}
