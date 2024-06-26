package com.vaspap.usermanagement.service;

import com.vaspap.usermanagement.dto.LoginUserRequest;
import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.dto.UserDto;

public interface UserService {
    UserDto registerUser(RegisterUserRequest registerUserRequest);
    UserDto loginUser(LoginUserRequest loginUserRequest);
}
