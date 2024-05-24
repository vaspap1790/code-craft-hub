package com.vaspap.usermanagement.factory;

import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.Student;
import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;
import org.springframework.stereotype.Component;

@Component("studentFactory")
public class StudentFactory implements UserFactory {
    @Override
    public User createUser(String username, String password, SubscriptionPlan subscription) {
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(password);
        student.setRole(Role.STUDENT);
        student.setSubscription(subscription);
        return student;
    }
}
