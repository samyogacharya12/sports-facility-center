package org.sports.facility.center.api;

import jakarta.mail.MessagingException;
import org.sports.facility.center.dto.RegisterUserDto;
import org.sports.facility.center.dto.RestResponse;
import org.sports.facility.center.dto.UserDto;
import org.sports.facility.center.service.AuthenticationService;
import org.sports.facility.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;


    @Autowired
    public AuthenticationService authenticationService;




    @PostMapping("/save")
    public ResponseEntity<UserDto> addNewUser(@RequestBody RegisterUserDto registerUserDto) throws MessagingException {
        registerUserDto.setCreatedDate(LocalDateTime.now());
        registerUserDto.setUpdatedDate(LocalDateTime.now());
        UserDto user = this.userService.save(registerUserDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/fetch")
    public ResponseEntity<UserDto> fetchUsers(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/fetch/username")
    public ResponseEntity<UserDto> fetchByUsername(@RequestBody UserDto userDto) {
        UserDto userDto1= this.userService.findByUsername(userDto.getUserName());
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<RestResponse> fetchUsers(@RequestParam(value = "token") String token) {
        return new ResponseEntity<>(userService.verifyAccount(token), HttpStatus.OK);
    }



    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        UserDto token= this.authenticationService.login(userDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
