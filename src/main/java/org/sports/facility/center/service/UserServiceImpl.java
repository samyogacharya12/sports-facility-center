package org.sports.facility.center.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);




    @Override
    public UserDto save(RegisterUserDto registerUserDto) {
        log.debug("saving user");
        try {
            Optional<User> optionalUser = this.userRepository.findByName(registerUserDto.getName());
            if (optionalUser.isPresent()) {
                throw new Invalid("Sorry UserName already exist", registerUserDto);
            }
            User userOptional = this.userRepository.findByEmail(registerUserDto.getEmail());
            if (Objects.nonNull(userOptional) && Objects.nonNull(userOptional.getId())) {
                throw new Invalid("Sorry Email already exist", registerUserDto);
            }
            registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
            User user = this.userMapper.toEntity(registerUserDto);
            UserDto userDto = this.userMapper.toDto(userRepository.save(user));
            registerUserDto.setUserId(userDto.getId());
            if (userDto.getRoles().equals(UserType.ROLE_CUSTOMER.toString())) {
                this.customerService.save(registerUserDto);
            }
            return this.adminService.save(registerUserDto);
        } catch (Exception exception) {
            log.error("save {}", exception);
        }    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
