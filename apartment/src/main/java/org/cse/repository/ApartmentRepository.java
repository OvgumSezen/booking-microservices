package org.cse.repository;

import org.cse.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    @Query(value = "SELECT * FROM apartments WHERE id = :id", nativeQuery = true)
    Apartment findApartmentById(@Param("id") Integer id);
}
