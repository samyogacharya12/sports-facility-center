package org.sports.facility.center.service;

import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.dto.UserInfoDetails;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

public interface JwtService {

    String extractUsername(String token);
    String generateToken(String userName);

    boolean validateToken(final String token, UserDetails userInfoDetails);

    Key getSignKey();
}
