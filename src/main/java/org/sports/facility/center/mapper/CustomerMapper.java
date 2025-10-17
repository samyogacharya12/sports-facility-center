package org.sports.facility.center.mapper;

import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.Customer;

import java.util.List;

public interface CustomerMapper {

    UserDto toDto(Customer customer);


    UserDto toDto(RegisterUserDto registerUserDto);
    List<UserDto> toDto(List<Customer> customers);


    Customer toEntity(UserDto userDto);
}
