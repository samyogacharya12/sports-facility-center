package org.sports.facility.center.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sports.facility.center.enumconstant.UserType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private UserType roles;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;


    @Column(name = "address", nullable = false)
    private String address;


    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    public User(String userName,
                String email,
                String password,
                UserType roles) {
        this.name=userName;
        this.email=email;
        this.password=password;
        this.roles=roles;

    }
}
