package com.vaspap.usermanagement.dto;

import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;

public record UserDto (String username, Role role, SubscriptionPlan subscription) {}
