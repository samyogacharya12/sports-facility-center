package org.sports.facility.center.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sports.facility.center.dto.ApiResponse;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.dto.UserInfoDetails;
import org.sports.facility.center.entity.User;
import org.sports.facility.center.exception.Invalid;
import org.sports.facility.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public UserDto login(UserDto userDto) {
        Authentication authenticate = authenticationManager.authenticate(new
            UsernamePasswordAuthenticationToken
            (userDto.getUserName(), userDto.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token= jwtService.generateToken(userDto.getUserName());
            userDto.setPassword(null);
            userDto.setUserName(null);
            userDto.setStatus(null);
            userDto.setDeleted(null);
            userDto.setToken(token);
            return userDto;
        } else {
            throw new RuntimeException("invalid access");
        }
    }



}
