package org.sports.facility.center.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends BaseDto {


    private Long id;
    private String userName;
    private String token;
    private String email;
    private String password;
    private String roles;
    private String address;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    private LocalDateTime createdDate;

    private LocalDateTime updateDate;

    public UserDto(Long id,
                   String userName,
                   String email,
                   String roles) {
        this.id=id;
        this.userName=userName;
        this.email=email;
        this.roles=roles;
    }

    public UserDto(String firstName,
                   String lastName,
                   String address,
                   String phoneNumber,
                   Long userId,
                   String userName,
                   LocalDateTime createdDate,
                   LocalDateTime updateDate,
                   String email,
                   String password,
                   String roles) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.id=userId;
        this.userName=userName;
        this.createdDate=createdDate;
        this.updateDate=updateDate;
        this.email=email;
        this.password=password;
        this.roles=roles;

    }
}
