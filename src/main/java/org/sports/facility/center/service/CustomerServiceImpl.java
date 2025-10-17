package org.sports.facility.center.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.Customer;
import org.sports.facility.center.mapper.CustomerMapper;
import org.sports.facility.center.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl
        .class);


    @Override
    public UserDto save(RegisterUserDto registerUserDto) {
        log.info("save");
        try {
            UserDto userDto = this.customerMapper.toDto(registerUserDto);
            Customer customer = this.customerMapper.toEntity(userDto);
            return this.customerMapper.toDto(this.customerRepository.save(customer));
        } catch (Exception exception){
            log.error("error {}", exception);
        }
        return null;
    }

    @Override
    public UserDto update(UserDto customerDto) {
        return null;
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}
