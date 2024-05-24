package com.vaspap.usermanagement.factory;

import com.vaspap.usermanagement.model.Admin;
import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;
import org.springframework.stereotype.Component;

@Component("adminFactory")
public class AdminFactory implements UserFactory {
    @Override
    public User createUser(String username, String password, SubscriptionPlan subscription) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setRole(Role.ADMIN);
        admin.setSubscription(subscription);
        return admin;
    }
}
