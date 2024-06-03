package com.vaspap.usermanagement.controller;

import com.vaspap.usermanagement.dto.LoginUserRequest;
import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.exception.AuthenticationFailedException;
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

    public static final String DATA_INTEGRITY_VIOLATION_MESSAGE = "Data integrity violation while registering user: ";
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "Illegal argument while registering user: ";
    public static final String UNEXPECTED_ERROR_REGISTER_MESSAGE = "Unexpected error while registering user: ";
    public static final String INVALID_USERNAME_OR_PASSWORD_MESSAGE = "Invalid username or password for user: ";
    public static final String UNEXPECTED_ERROR_LOGIN_MESSAGE = "Unexpected error while logging in user: ";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        try {
            UserDto userDto = userService.registerUser(registerUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (DataIntegrityViolationException e) {
            LOGGER.log(Level.SEVERE, DATA_INTEGRITY_VIOLATION_MESSAGE + registerUserRequest.username(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(DATA_INTEGRITY_VIOLATION_MESSAGE + registerUserRequest.username());
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, ILLEGAL_ARGUMENT_MESSAGE + registerUserRequest.username(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ILLEGAL_ARGUMENT_MESSAGE + registerUserRequest.username());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, UNEXPECTED_ERROR_REGISTER_MESSAGE + registerUserRequest.username(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR_REGISTER_MESSAGE + registerUserRequest.username());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserRequest loginUserRequest){
        try {
            UserDto userDto = userService.loginUser(loginUserRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDto);
        }catch (AuthenticationFailedException e) {
            LOGGER.log(Level.SEVERE, INVALID_USERNAME_OR_PASSWORD_MESSAGE + loginUserRequest.username(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_USERNAME_OR_PASSWORD_MESSAGE + loginUserRequest.username());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, UNEXPECTED_ERROR_LOGIN_MESSAGE + loginUserRequest.username(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR_LOGIN_MESSAGE + loginUserRequest.username());
        }
    }
}
