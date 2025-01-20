package org.cse.repository;

import org.cse.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchApartmentRepository extends JpaRepository<Apartment, Integer> {
    @Query(value = "SELECT a.id FROM Apartment a")
    List<Integer> findAllApartmentIds();

    @Query(value = "SELECT a FROM Apartment a WHERE a.id IN :ids")
    List<Apartment> findAvailableApartmentsByIds(List<Integer> ids);
}
