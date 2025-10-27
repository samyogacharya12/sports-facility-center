package org.sports.facility.center.mapper;

import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.Admin;

import java.util.List;

public interface AdminMapper {

    UserDto toDto(Admin admin);


    UserDto toDto(RegisterUserDto registerUserDto);
    List<UserDto> toDto(List<Admin> admins);


    Admin toEntity(UserDto userDto);
}
