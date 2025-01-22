package org.cse.repository;

import org.cse.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchBookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT DISTINCT b.apartmentId FROM Booking b WHERE ((b.to > :from AND b.from < :from) OR (b.to > :to AND b.from < :to) OR (b.from >= :from AND b.to <= :to))")
    List<Integer> findBookedApartmentIds(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query(value = "SELECT DISTINCT b FROM Booking b WHERE b.id = :id")
    Booking findBookingById(@Param("id") Integer id);
}
