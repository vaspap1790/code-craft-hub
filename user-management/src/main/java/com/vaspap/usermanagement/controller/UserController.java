package com.vaspap.usermanagement.controller;

import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.UserDto;
import com.vaspap.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public final class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(String username, String password, Role role, SubscriptionPlan subscription) {
        return ResponseEntity.ok(userService.registerUser(username, password, role, subscription));
    }
}
