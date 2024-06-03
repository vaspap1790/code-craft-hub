package com.vaspap.usermanagement.util;

import com.vaspap.usermanagement.dto.LoginUserRequest;
import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.dto.UserDto;
import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;

public class TestData {
    public static RegisterUserRequest createValidRegisterUserRequest() {
        return new RegisterUserRequest("username", "password", Role.ADMIN, SubscriptionPlan.FREE);
    }

    public static LoginUserRequest createLoginUserRequest() {
        return new LoginUserRequest("username", "password");
    }

    public static RegisterUserRequest createRegisterUserRequestInvalidUsername() {
        return new RegisterUserRequest("ab", "password", Role.ADMIN, SubscriptionPlan.FREE);
    }

    public static RegisterUserRequest createRegisterUserRequestInvalidPassword() {
        return new RegisterUserRequest("user123", "1234", Role.ADMIN, SubscriptionPlan.FREE);
    }

    public static RegisterUserRequest createRegisterUserRequestNullRole() {
        return new RegisterUserRequest("user123", "password", null, SubscriptionPlan.FREE);
    }

    public static RegisterUserRequest createRegisterUserRequestNullSubscription() {
        return new RegisterUserRequest("user123", "password", Role.ADMIN, null);
    }

    public static UserDto createUserDto() {
        return new UserDto("username", Role.ADMIN, SubscriptionPlan.FREE);
    }
}
