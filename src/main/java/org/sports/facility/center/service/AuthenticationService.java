package org.sports.facility.center.service;

import org.sports.facility.center.dto.UserDto;

public interface AuthenticationService {

    UserDto login(UserDto userDto);

}
