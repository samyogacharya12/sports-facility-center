package org.sports.facility.center.service;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.User;
import org.sports.facility.center.enumconstant.UserType;
import org.sports.facility.center.exception.Invalid;
import org.sports.facility.center.mapper.UserMapper;
import org.sports.facility.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EmailService emailService;




    @Override
    public UserDto save(RegisterUserDto registerUserDto) throws MessagingException {
        log.debug("saving user");
        try {
            Optional<User> optionalUser = this.userRepository.findByName(registerUserDto.getUserName());
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
            userDto.setUserName(registerUserDto.getUserName());
            if (userDto.getRoles().equals(UserType.ROLE_CUSTOMER.toString())) {
                if (Objects.nonNull(userDto.getId())) {
                    emailService.sendHtmlEmail(
                        userDto.getEmail(),
                        "Registration for Lambright Sports Facility Center",
                        "<h3>Welcome, " + userDto.getUserName() + "!</h3>" +
                            "<p>Thank you for registering at <b>Lambright Sports Facility Center Gym</b>.</p>" +
                            "<p>We’re excited to have you join our community!</p>" +
                            "<p>Best regards,<br>Lambright Sports Facility Center Team</p>"
                    );
                }
            } else {
                userDto=this.adminService.save(registerUserDto);
            }
            return userDto;

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
