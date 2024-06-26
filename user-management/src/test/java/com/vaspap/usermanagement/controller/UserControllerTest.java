package com.vaspap.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaspap.usermanagement.dto.LoginUserRequest;
import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.exception.AuthenticationFailedException;
import com.vaspap.usermanagement.service.UserService;
import com.vaspap.usermanagement.dto.UserDto;
import com.vaspap.usermanagement.config.TestConfig;
import com.vaspap.usermanagement.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import com.vaspap.usermanagement.util.TestData;

import static com.vaspap.usermanagement.model.Role.ADMIN;
import static com.vaspap.usermanagement.model.SubscriptionPlan.FREE;
import static com.vaspap.usermanagement.util.TestData.BASE_URL;
import static com.vaspap.usermanagement.util.TestData.LOGIN_URL;
import static com.vaspap.usermanagement.util.TestData.REGISTER_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static com.vaspap.usermanagement.util.MessageConstants.*;

@WebMvcTest(UserController.class)
@Import({TestConfig.class})
@AutoConfigureMockMvc(addFilters = false) // Exclude default filters (to disable security)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void registerUser_ShouldReturnCreated_WhenUserRegistrationIsSuccessful() throws Exception {
        // given
        RegisterUserRequest request = TestData.createValidRegisterUserRequest();
        UserDto userDto = TestData.createUserDto();

        // when
        when(userService.registerUser(any(RegisterUserRequest.class))).thenReturn(userDto);

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, request)
        // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.role").value(ADMIN.name()))
                .andExpect(jsonPath("$.subscription").value(FREE.name()))
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

    @Test
    public void registerUser_ShouldReturnConflict_WhenDataIntegrityViolationOccurs() throws Exception {
        // given
        RegisterUserRequest request = TestData.createValidRegisterUserRequest();

        // when
        doThrow(new DataIntegrityViolationException("")).when(userService).registerUser(any(RegisterUserRequest.class));

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, request)
        // then
                .andExpect(status().isConflict())
                .andExpect(content().string(DATA_INTEGRITY_VIOLATION_MESSAGE + request.username()))
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

    @Test
    public void registerUser_ShouldReturnBadRequest_WhenIllegalArgumentExceptionOccurs() throws Exception {
        // given
        RegisterUserRequest request = TestData.createValidRegisterUserRequest();

        // when
        doThrow(new IllegalArgumentException("")).when(userService).registerUser(any(RegisterUserRequest.class));

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, request)
        // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ILLEGAL_ARGUMENT_MESSAGE + request.username()))
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

    @Test
    public void registerUser_ShouldReturnInternalServerError_WhenUnexpectedExceptionOccurs() throws Exception {
        // given
        RegisterUserRequest request = TestData.createValidRegisterUserRequest();

        // when
        doThrow(new RuntimeException("")).when(userService).registerUser(any(RegisterUserRequest.class));

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, request)
        // then
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(UNEXPECTED_ERROR_REGISTER_MESSAGE + request.username()))
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

    @Test
    public void loginUser_ShouldReturnAccepted_WhenLoginIsSuccessful() throws Exception {
        // given
        LoginUserRequest request = TestData.createLoginUserRequest();
        UserDto userDto = TestData.createUserDto();

        // when
        when(userService.loginUser(any(LoginUserRequest.class))).thenReturn(userDto);

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + LOGIN_URL, request)
        // then
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.role").value(ADMIN.name()))
                .andExpect(jsonPath("$.subscription").value(FREE.name()))
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).loginUser(any(LoginUserRequest.class));
    }

    @Test
    public void loginUser_ShouldReturnBadRequest_WhenAuthenticationFailedExceptionOccurs() throws Exception {
        // given
        LoginUserRequest request = TestData.createLoginUserRequest();

        // when
        doThrow(new AuthenticationFailedException("")).when(userService).loginUser(any(LoginUserRequest.class));

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + LOGIN_URL, request)
        // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(INVALID_USERNAME_OR_PASSWORD_MESSAGE + request.username()))
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).loginUser(any(LoginUserRequest.class));
    }

    @Test
    public void loginUser_ShouldReturnInternalServerError_WhenUnexpectedExceptionOccurs() throws Exception {
        // given
        LoginUserRequest request = TestData.createLoginUserRequest();

        // when
        doThrow(new RuntimeException("")).when(userService).loginUser(any(LoginUserRequest.class));

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + LOGIN_URL, request)
        // then
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(UNEXPECTED_ERROR_LOGIN_MESSAGE + request.username()))
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).loginUser(any(LoginUserRequest.class));
    }

}