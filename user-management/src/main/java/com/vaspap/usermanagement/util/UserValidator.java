package com.vaspap.usermanagement.util;

import com.vaspap.usermanagement.dto.RegisterUserRequest;

public class UserValidator {
    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 20;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 5 && password.length() <= 20;
    }

    public static void validateInput(RegisterUserRequest registerUserRequest) {
        if (!isValidUsername(registerUserRequest.username()) || !isValidPassword(registerUserRequest.password())
                || registerUserRequest.role() == null || registerUserRequest.subscription() == null) {
            throw new IllegalArgumentException("Invalid input parameters for user registration");
        }
    }
}
