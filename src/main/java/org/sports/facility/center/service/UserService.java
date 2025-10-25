package org.sports.facility.center.service;

import jakarta.mail.MessagingException;
import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.RestResponse;
import org.sports.facility.center.dto.UserDto;

import java.util.List;

public interface UserService {


    UserDto save(RegisterUserDto registerUserDto) throws MessagingException;

    UserDto update(UserDto userDto);

    UserDto findById(Long id);

    UserDto findByUsername(String username);

    void delete(Long id);

    List<UserDto> findAll();

    RestResponse verifyAccount(String token);
}
