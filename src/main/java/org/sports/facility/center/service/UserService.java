package org.sports.facility.center.service;

import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;

import java.util.List;

public interface UserService {


    UserDto save(RegisterUserDto registerUserDto);

    UserDto update(UserDto userDto);

    UserDto findById(Long id);

    void delete(Long id);

    List<UserDto> findAll();
}
