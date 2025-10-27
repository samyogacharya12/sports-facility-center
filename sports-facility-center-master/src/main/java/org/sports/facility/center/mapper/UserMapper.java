package org.sports.facility.center.mapper;

import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.User;

import java.util.List;

public interface UserMapper {


    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);


    User toEntity(UserDto userDto);


    User toEntity(RegisterUserDto registerUserDto);
}
