package com.vaspap.usermanagement.service;

import com.vaspap.usermanagement.dto.LoginUserRequest;
import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.dto.UserDto;
import com.vaspap.usermanagement.exception.AuthenticationFailedException;
import com.vaspap.usermanagement.factory.UserFactory;
import com.vaspap.usermanagement.factory.UserFactoryProducer;
import com.vaspap.usermanagement.model.User;
import com.vaspap.usermanagement.repository.UserRepository;
import com.vaspap.usermanagement.state.SubscriptionState;
import com.vaspap.usermanagement.state.SubscriptionStateFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.vaspap.usermanagement.util.TestData;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFactoryProducer userFactoryProducer;

    @Mock
    private SubscriptionStateFactory subscriptionStateFactory;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserFactory userFactory;

    @Mock
    private SubscriptionState subscriptionState;

    @Mock
    private User user;

    @Test
    public void testRegisterUser_Success() {
        // given
        RegisterUserRequest validRequest = TestData.createValidRegisterUserRequest();

        // when
        when(userFactoryProducer.getFactory(validRequest.role().name())).thenReturn(userFactory);
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> new BCryptPasswordEncoder().encode((String) invocation.getArgument(0)));        when(userFactory.createUser(anyString(), anyString(), any())).thenReturn(user);
        when(subscriptionStateFactory.getState(validRequest.subscription().name())).thenReturn(subscriptionState);
        when(user.toUserDto()).thenReturn(TestData.createUserDto());

        UserDto userDto = userServiceImpl.registerUser(validRequest);

        // then
        assertNotNull(userDto);
        verify(userRepository, times(1)).save(user);
        verify(user).setSubscriptionState(subscriptionState);
    }

    @Test
    public void testRegisterUser_InvalidInput() {
        // given
        RegisterUserRequest invalidRequest = TestData.createRegisterUserRequestNullRole();

        // when
        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.registerUser(invalidRequest));

        // then
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLoginUser_Success() {
        // given
        LoginUserRequest validRequest = TestData.createLoginUserRequest();
        when(userRepository.findByUsername(validRequest.username())).thenReturn(user);
        when(passwordEncoder.matches(validRequest.password(), user.getPassword())).thenReturn(true);
        when(user.toUserDto()).thenReturn(TestData.createUserDto());

        // when
        UserDto userDto = userServiceImpl.loginUser(validRequest);

        // then
        assertNotNull(userDto);
    }

    @Test
    public void testLoginUser_Failure() {
        // given
        LoginUserRequest invalidRequest = TestData.createLoginUserRequest();
        when(userRepository.findByUsername(invalidRequest.username())).thenReturn(null);

        // when
        assertThrows(AuthenticationFailedException.class, () -> userServiceImpl.loginUser(invalidRequest));
    }
}