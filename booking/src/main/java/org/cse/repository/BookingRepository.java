package org.cse.repository;

import org.cse.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT * FROM bookings WHERE id = :id", nativeQuery = true)
    Booking findBookingById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Bookings", nativeQuery = true)
    void removeBookings();
}
