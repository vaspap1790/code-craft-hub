package com.vaspap.usermanagement.factory;

import com.vaspap.usermanagement.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class UserFactoryProducer {

    private static final Logger LOGGER = Logger.getLogger(UserFactoryProducer.class.getName());

    private final Map<String, UserFactory> userFactoryMap;

    @Autowired
    public UserFactoryProducer(@Qualifier("adminFactory") UserFactory adminFactory,
                               @Qualifier("studentFactory") UserFactory studentFactory,
                               @Qualifier("writerFactory") UserFactory writerFactory){
        userFactoryMap = Map.of(
                Role.ADMIN.name(), adminFactory,
                Role.STUDENT.name(), studentFactory,
                Role.WRITER.name(), writerFactory
        );
    }

    public UserFactory getFactory(String role){
        UserFactory factory = userFactoryMap.get(role);
        if (factory == null) {
            LOGGER.severe("Unknown user role: " + role);
            throw new IllegalArgumentException("Unknown user role: " + role);
        }
        return factory;
    }

}
