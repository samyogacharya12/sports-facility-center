package org.sports.facility.center.mapper;

import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.Admin;
import org.sports.facility.center.entity.User;
import org.sports.facility.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminMapperImpl implements AdminMapper{
    @Autowired
    private UserRepository userRepository;

    private static UserDto  mapToAdminDto(Admin admin){
        return UserDto.builder()
            .address(admin.getAddress())
            .firstName(admin.getFirstName())
            .lastName(admin.getLastName())
            .phoneNumber(admin.getPhoneNumber())
            .build();
    }
    @Override
    public UserDto toDto(Admin admin) {
        Optional<User> user = this.userRepository
            .findById(admin.getUserInfo().getId());
        if (user.isPresent()) {
            return mapToAdminDto(admin);
        }
        return new UserDto();
    }

    @Override
    public UserDto toDto(RegisterUserDto registerUserDto) {
        UserDto adminDto = new UserDto(
            registerUserDto.getFirstName(),
            registerUserDto.getLastName()
            , registerUserDto.getAddress(),
            registerUserDto.getPhoneNumber(),
            registerUserDto.getUserId(),
            registerUserDto.getUserName());
        adminDto.setId(registerUserDto.getUserId());
        adminDto.setCreatedDate(registerUserDto.getCreatedDate());
        adminDto.setUpdatedDate(registerUserDto.getUpdatedDate());
        return adminDto;
    }

    @Override
    public List<UserDto> toDto(List<Admin> admins) {
        return admins.stream().map(AdminMapperImpl::mapToAdminDto)
            .collect(Collectors.toList());
    }

    @Override
    public Admin toEntity(UserDto userDto) {
        Admin admin = Admin.builder()
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .address(userDto.getAddress())
            .phoneNumber(userDto.getPhoneNumber()
            )
            .build();
        admin.setCreatedDate(userDto.getCreatedDate().toString());
        admin.setUpdatedDate(userDto.getUpdatedDate().toString());
        return admin;
    }
}
