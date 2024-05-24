package com.vaspap.usermanagement.service;

import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.factory.UserFactory;
import com.vaspap.usermanagement.factory.UserFactoryProducer;
import com.vaspap.usermanagement.dto.UserDto;
import com.vaspap.usermanagement.model.User;
import com.vaspap.usermanagement.repository.UserRepository;
import com.vaspap.usermanagement.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final UserFactoryProducer userFactoryProducer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserFactoryProducer userFactoryProducer) {
        this.userRepository = userRepository;
        this.userFactoryProducer = userFactoryProducer;
    }

    @Override
    public UserDto registerUser(RegisterUserRequest registerUserRequest) {
        UserValidator.validateInput(registerUserRequest);

        UserFactory userFactory = userFactoryProducer.getFactory(registerUserRequest.role().name());
        User user = userFactory.createUser(registerUserRequest.username(), registerUserRequest.password(),
                registerUserRequest.subscription());

        userRepository.save(user);

        LOGGER.info("User registered successfully: " + registerUserRequest.username());
        return user.toUserDto();
    }

}
