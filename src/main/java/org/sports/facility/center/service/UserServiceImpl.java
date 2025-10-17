package org.sports.facility.center.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.dto.UserInfoDetails;
import org.sports.facility.center.entity.User;
import org.sports.facility.center.enumconstant.UserType;
import org.sports.facility.center.exception.Invalid;
import org.sports.facility.center.mapper.UserMapper;
import org.sports.facility.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private CustomerService customerService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AdminService adminService;




    @Override
    public UserDto save(RegisterUserDto registerUserDto) {
        log.debug("saving user");
        try {
            Optional<User> optionalUser = this.userRepository.findByName(registerUserDto.getName());
            if (optionalUser.isPresent()) {
                throw new Invalid("Sorry UserName already exist", registerUserDto);
            }
            Optional<User> userOptional = this.userRepository.findByEmail(registerUserDto.getEmail());
            if (userOptional.isPresent() && Objects.nonNull(userOptional.get().getId())) {
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
        }
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto findById(String token, Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> userMapper.toDto(value)).orElse(null);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

}
