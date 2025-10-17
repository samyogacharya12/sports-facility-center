package org.sports.facility.center.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.entity.Admin;
import org.sports.facility.center.entity.User;
import org.sports.facility.center.mapper.AdminMapper;
import org.sports.facility.center.repository.AdministratorRepository;
import org.sports.facility.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdministratorRepository administratorRepository;

    private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Override
    public UserDto save(RegisterUserDto registerUserDto) {
        log.info("save");
        try {
            UserDto userDto = this.adminMapper.toDto(registerUserDto);
            Optional<User> user=this.userRepository.findById(userDto.getId());
            Admin admin = this.adminMapper.toEntity(userDto);
            user.ifPresent(admin::setUserInfo);
            return this.adminMapper.toDto(this.administratorRepository.save(admin));
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
