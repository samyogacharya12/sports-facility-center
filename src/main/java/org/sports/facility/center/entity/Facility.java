package org.sports.facility.center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@Table(name = "facility")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Facility extends BaseEntity {


    @Column(name = "facility_name")
    private String facilityName;

    @Column(name = "description")
    private String description;


    @Column(name = "capacity")
    private Integer capacity;


    @Column(name = "opening_time")
    private String openingTime;


    @Column(name = "closing_time")
    private String closingTime;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookingList;
}
