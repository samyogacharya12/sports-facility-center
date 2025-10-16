package org.sports.facility.center.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserDto extends BaseDto implements Serializable {

    private String name;
    private String email;
    private String password;
    private String roles;
    private String firstName;
    private String lastName;
    private String address;
    private String salary;
    private String subject;
    private String phoneNumber;

    private Long userId;
    private String userName;

    private Long customerId;

    private Long adminId;
}
