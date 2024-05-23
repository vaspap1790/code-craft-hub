package com.vaspap.usermanagement.service;

import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;
import com.vaspap.usermanagement.model.UserDto;

public interface UserService {
    UserDto registerUser(String username, String password, Role role, SubscriptionPlan subscription);
    User loginUser(String username, String password);
}
