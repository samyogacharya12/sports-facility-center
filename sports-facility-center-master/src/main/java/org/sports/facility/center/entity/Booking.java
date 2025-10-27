package org.sports.facility.center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sports.facility.center.enumconstant.BookingStatus;


@Data
@Builder
@Table(name = "booking")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "facility_id")
    private Facility facility;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "booking_date", nullable = false)
    private String bookingDate;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Column(name = "booking_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;


}
