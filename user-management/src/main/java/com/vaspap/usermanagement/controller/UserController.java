package com.vaspap.usermanagement.controller;

import com.vaspap.usermanagement.dto.RegisterUserRequest;
import org.springframework.dao.DataIntegrityViolationException;
import com.vaspap.usermanagement.dto.UserDto;
import com.vaspap.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/users")
public final class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        try {
            UserDto userDto = userService.registerUser(registerUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (DataIntegrityViolationException e) {
            LOGGER.log(Level.SEVERE, "Data integrity violation while registering user: " + registerUserRequest.username(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Illegal argument while registering user: " + registerUserRequest.username(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error while registering user: " + registerUserRequest.username(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
