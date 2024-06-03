package com.vaspap.usermanagement.service;

import com.vaspap.usermanagement.exception.AuthenticationFailedException;
import com.vaspap.usermanagement.dto.LoginUserRequest;
import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.factory.UserFactory;
import com.vaspap.usermanagement.factory.UserFactoryProducer;
import com.vaspap.usermanagement.dto.UserDto;
import com.vaspap.usermanagement.model.User;
import com.vaspap.usermanagement.repository.UserRepository;
import com.vaspap.usermanagement.state.SubscriptionState;
import com.vaspap.usermanagement.state.SubscriptionStateFactory;
import com.vaspap.usermanagement.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final UserFactoryProducer userFactoryProducer;
    private final SubscriptionStateFactory subscriptionStateFactory;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserFactoryProducer userFactoryProducer,
                           SubscriptionStateFactory subscriptionStateFactory, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userFactoryProducer = userFactoryProducer;
        this.subscriptionStateFactory = subscriptionStateFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(RegisterUserRequest registerUserRequest) {
        UserValidator.validateInput(registerUserRequest);

        UserFactory userFactory = userFactoryProducer.getFactory(registerUserRequest.role().name());
        User user = userFactory.createUser(registerUserRequest.username(), passwordEncoder.encode(registerUserRequest.password()),
                registerUserRequest.subscription());

        SubscriptionState subscriptionState = subscriptionStateFactory.getState(registerUserRequest.subscription().name());
        user.setSubscriptionState(subscriptionState);

        userRepository.save(user);

        LOGGER.info("User registered successfully: " + registerUserRequest.username());
        return user.toUserDto();
    }

    @Override
    public UserDto loginUser(LoginUserRequest loginUserRequest){
        User user = userRepository.findByUsername(loginUserRequest.username());
        if (user == null || !passwordEncoder.matches(loginUserRequest.password(), user.getPassword())) {
            throw new AuthenticationFailedException("Invalid username or password for user " + loginUserRequest.username());
        }
        LOGGER.info("User logged in successfully: " + user.getUsername());
        return user.toUserDto();
    }

}
