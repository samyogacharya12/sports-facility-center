package org.sports.facility.center.mapper;

import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.Customer;
import org.sports.facility.center.entity.User;
import org.sports.facility.center.repository.CustomerRepository;
import org.sports.facility.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerMapperImpl implements CustomerMapper{

    @Autowired

    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;


    private static UserDto  mapToCustomerDto(Customer customer){
        return UserDto.builder()
            .address(customer.getAddress())
            .firstName(customer.getFirstName())
            .lastName(customer.getLastName())
            .phoneNumber(customer.getPhoneNumber())
            .build();
    }
    @Override
    public UserDto toDto(Customer customer) {
        Optional<User> user = this.userRepository
            .findById(customer.getUserInfo().getId());
        if (user.isPresent()) {
            return mapToCustomerDto(customer);
        }
        return new UserDto();
    }

    @Override
    public UserDto toDto(RegisterUserDto registerUserDto) {
        UserDto userDto= new UserDto(
            registerUserDto.getFirstName(),
            registerUserDto.getLastName()
            , registerUserDto.getAddress(),
            registerUserDto.getPhoneNumber(),
            registerUserDto.getUserId(),
            registerUserDto.getUserName(),
            registerUserDto.getCreatedDate(),
            registerUserDto.getUpdatedDate(),
            registerUserDto.getEmail(),
            registerUserDto.getPassword(),
            registerUserDto.getRoles());
        userDto.setCreatedDate(registerUserDto.getCreatedDate());
        userDto.setUpdatedDate(registerUserDto.getUpdatedDate());
        return userDto;
    }

    @Override
    public List<UserDto> toDto(List<Customer> customers) {
        return customers.stream().map(CustomerMapperImpl::mapToCustomerDto)
            .collect(Collectors.toList());
    }

    @Override
    public Customer toEntity(UserDto userDto) {
        Customer customer= Customer.builder()
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .address(userDto.getAddress())
            .phoneNumber(userDto.getPhoneNumber())
            .userInfo(userMapper.toEntity(userDto))
            .build();
        customer.setCreatedDate(LocalDateTime.now().toString());
        customer.setUpdatedDate(LocalDateTime.now().toString());
        return customer;
    }
}
