package org.sports.facility.center.repository;

import org.sports.facility.center.entity.Booking;
import org.sports.facility.center.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
        "WHERE b.facility.id = :facilityId " +
        "AND b.bookingDate = :bookingDate " +
        "AND b.bookingStatus IN ('BOOKED', 'HALF_BOOKED')")
    List<Booking> findActiveBookings(
        @Param("facilityId") Long facilityId,
        @Param("bookingDate") String bookingDate);
}
