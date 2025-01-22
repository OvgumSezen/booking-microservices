package org.cse.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.cse.model.Apartment;
import org.cse.repository.SearchApartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchApartmentService {
    private final SearchApartmentRepository searchApartmentRepository;
    private final EntityManager entityManager;

    @Transactional
    public void createApartment(Apartment apartment) {
        apartment.setId(null);
        searchApartmentRepository.save(apartment);
    }

    @Transactional
    public void removeApartment(Integer apartmentId) {
        searchApartmentRepository.deleteById(apartmentId);
    }
}
