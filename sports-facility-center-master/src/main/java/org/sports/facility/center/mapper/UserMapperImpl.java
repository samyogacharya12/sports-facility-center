package org.sports.facility.center.mapper;

import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.User;
import org.sports.facility.center.enumconstant.UserType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        return mapToUserDto(user);
    }

    private static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto(user.getId(),
            user.getName()
            , user.getEmail(),
            user.getRoles().toString());
        userDto.setCreatedDate(LocalDateTime.parse(user.getCreatedDate()));
        userDto.setUpdatedDate(LocalDateTime.parse(user.getUpdatedDate()));
        userDto.setAddress(userDto.getAddress());
        userDto.setFirstName(userDto.getFirstName());
        userDto.setLastName(userDto.getLastName());
        userDto.setPassword(null);
        return userDto;
    }


    @Override
    public List<UserDto> toDto(List<User> users) {
        return users.stream().map(UserMapperImpl::mapToUserDto)
            .collect(Collectors.toList());
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User(userDto.getUserName(), userDto.getEmail(),
            userDto.getPassword(), Enum.valueOf(UserType.class, userDto.getRoles()));
        user.setId(userDto.getId());
        user.setCreatedDate(userDto.getCreatedDate().toString());
        user.setUpdatedDate(userDto.getUpdatedDate().toString());
        user.setStatus(true);
        user.setDeleted(false);
        return user;
    }

    @Override
    public User toEntity(RegisterUserDto registerUserDto) {
        User user = new User(registerUserDto.getUserName(), registerUserDto.getEmail(),
            registerUserDto.getPassword(),
            Enum.valueOf(UserType.class, registerUserDto.getRoles()));
        user.setCreatedDate(registerUserDto.getCreatedDate().toString());
        user.setUpdatedDate(registerUserDto.getUpdatedDate().toString());
        if(!user.getStatus()) {
            user.setStatus(false);
        }else {
            user.setStatus(true);
        }
        user.setDeleted(false);
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());
        user.setAddress(registerUserDto.getAddress());
        user.setPhoneNumber(registerUserDto.getPhoneNumber());
        return user;
    }
}
