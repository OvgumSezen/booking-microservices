package org.cse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cse.model.Apartment;
import org.cse.mq.ApartmentPublisher;
import org.cse.repository.ApartmentRepository;
import org.cse.response.ApartmentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentPublisher apartmentPublisher;

    @Transactional
    public ApartmentResponse add(String name, String address, Integer noiseLevel, Integer floor) {
        Apartment apartment = Apartment.builder()
                .name(name)
                .address(address)
                .noiseLevel(noiseLevel)
                .floor(floor)
                .build();

        apartment = apartmentRepository.save(apartment);

        apartmentPublisher.publishApartmentCreated(apartment);

        return ApartmentResponse.builder()
                .id(apartment.getId())
                .name(apartment.getName())
                .address(apartment.getAddress())
                .noiseLevel(apartment.getNoiseLevel())
                .floor(apartment.getFloor())
                .build();
    }

    @Transactional
    public ApartmentResponse remove(Integer id) {
        Apartment apartment = apartmentRepository.findApartmentById(id);
        apartmentRepository.deleteById(id);

        apartmentPublisher.publishApartmentRemoved(apartment);

        return ApartmentResponse.builder()
                .id(apartment.getId())
                .name(apartment.getName())
                .address(apartment.getAddress())
                .noiseLevel(apartment.getNoiseLevel())
                .floor(apartment.getFloor())
                .build();
    }


    public List<ApartmentResponse> list() {
        List<Apartment> apartments = apartmentRepository.findAll();
        return apartments.stream().map(apartment -> ApartmentResponse.builder()
            .id(apartment.getId())
            .name(apartment.getName())
            .address(apartment.getAddress())
            .noiseLevel(apartment.getNoiseLevel())
            .floor(apartment.getFloor())
            .build())
                .toList();
    }
}
