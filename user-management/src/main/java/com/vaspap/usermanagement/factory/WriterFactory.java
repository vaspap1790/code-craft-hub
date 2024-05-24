package com.vaspap.usermanagement.factory;

import com.vaspap.usermanagement.model.Writer;
import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;
import org.springframework.stereotype.Component;

@Component("writerFactory")
public class WriterFactory implements UserFactory {
    @Override
    public User createUser(String username, String password, SubscriptionPlan subscription) {
        Writer writer = new Writer();
        writer.setUsername(username);
        writer.setPassword(password);
        writer.setRole(Role.WRITER);
        writer.setSubscription(subscription);
        return writer;
    }
}
