package com.vaspap.usermanagement.service;

import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.factory.UserFactory;
import com.vaspap.usermanagement.factory.UserFactoryProducer;
import com.vaspap.usermanagement.dto.UserDto;
import com.vaspap.usermanagement.model.User;
import com.vaspap.usermanagement.repository.UserRepository;
import com.vaspap.usermanagement.state.SubscriptionState;
import com.vaspap.usermanagement.state.SubscriptionStateFactory;
import com.vaspap.usermanagement.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final UserFactoryProducer userFactoryProducer;
    private final SubscriptionStateFactory subscriptionStateFactory;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserFactoryProducer userFactoryProducer,
                           SubscriptionStateFactory subscriptionStateFactory) {
        this.userRepository = userRepository;
        this.userFactoryProducer = userFactoryProducer;
        this.subscriptionStateFactory = subscriptionStateFactory;
    }

    @Override
    public UserDto registerUser(RegisterUserRequest registerUserRequest) {
        UserValidator.validateInput(registerUserRequest);

        UserFactory userFactory = userFactoryProducer.getFactory(registerUserRequest.role().name());
        User user = userFactory.createUser(registerUserRequest.username(), registerUserRequest.password(),
                registerUserRequest.subscription());

        SubscriptionState subscriptionState = subscriptionStateFactory.getState(registerUserRequest.subscription().name());
        user.setSubscriptionState(subscriptionState);

        userRepository.save(user);

        LOGGER.info("User registered successfully: " + registerUserRequest.username());
        return user.toUserDto();
    }

}
