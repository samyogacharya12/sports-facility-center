package org.sports.facility.center.service;

import jakarta.mail.MessagingException;
import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;

import java.util.List;

public interface UserService {


    UserDto save(RegisterUserDto registerUserDto) throws MessagingException;

    UserDto update(UserDto userDto);

    UserDto findById(String token,Long id);

    void delete(Long id);

    List<UserDto> findAll();
}
