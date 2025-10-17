package org.sports.facility.center.service;

import org.sports.facility.center.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

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
