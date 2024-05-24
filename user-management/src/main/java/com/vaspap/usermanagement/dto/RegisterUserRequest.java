package com.vaspap.usermanagement.dto;

import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;

public record RegisterUserRequest(String username, String password, Role role, SubscriptionPlan subscription) {}
