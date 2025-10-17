package org.sports.facility.center.service;

import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;

import java.util.List;

public interface CustomerService {

    UserDto save(RegisterUserDto registerUserDto);

    UserDto update(UserDto customerDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    void delete(Long id);
}
