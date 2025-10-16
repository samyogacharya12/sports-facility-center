package org.sports.facility.center.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "administrator")
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;


    @Column(name = "address", nullable = false)
    private String address;


    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;


    @OneToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userInfo;
}
