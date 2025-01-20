package org.cse.service;

import lombok.RequiredArgsConstructor;
import org.cse.model.Apartment;
import org.cse.repository.SearchApartmentRepository;
import org.cse.repository.SearchBookingRepository;
import org.cse.response.ApartmentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchBookingRepository searchBookingRepository;
    private final SearchApartmentRepository searchApartmentRepository;

    public List<ApartmentResponse> search(LocalDateTime from, LocalDateTime to) {
        List<Integer> bookedApartmentIds = searchBookingRepository.findBookedApartmentIds(from, to);
        List<Integer> apartmentIds = searchApartmentRepository.findAllApartmentIds();

        System.out.println(bookedApartmentIds);
        System.out.println(apartmentIds);

        List<Integer> availableApartmentIds = getAvailableApartmentIds(apartmentIds, bookedApartmentIds);
        List<Apartment> availableApartments = searchApartmentRepository.findAvailableApartmentsByIds(availableApartmentIds);

        return mapApartmentsToResponses(availableApartments);
    }

    private static List<ApartmentResponse> mapApartmentsToResponses(List<Apartment> availableApartments) {
        return availableApartments.stream().map(apartment -> ApartmentResponse.builder()
                        .id(apartment.getId())
                        .name(apartment.getName())
                        .address(apartment.getAddress())
                        .noiseLevel(apartment.getNoiseLevel())
                        .floor(apartment.getFloor())
                        .build())
                .toList();
    }

    private static List<Integer> getAvailableApartmentIds(List<Integer> apartmentIds, List<Integer> bookedApartmentIds) {
        List<Integer> availableApartmentIds = new ArrayList<>();
        for(Integer id : apartmentIds) {
            if(!bookedApartmentIds.contains(id)) {
               availableApartmentIds.add(id);
            }
        }
        return availableApartmentIds;
    }
}
