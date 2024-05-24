package com.vaspap.usermanagement.factory;

import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;

public interface UserFactory {
    User createUser(String username, String password, SubscriptionPlan subscription);
}
