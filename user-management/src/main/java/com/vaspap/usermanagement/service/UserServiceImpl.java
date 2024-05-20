package com.vaspap.usermanagement.service;

import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;
import com.vaspap.usermanagement.model.UserDto;
import com.vaspap.usermanagement.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDto registerUser(String username, String password, Role role, SubscriptionPlan subscription) {
        User user = new User(username, password, role, subscription);
        return userRepository.save(user).toUserDto();
    }

    @Override
    public User loginUser(String username, String password) {
        return null;
    }
}
