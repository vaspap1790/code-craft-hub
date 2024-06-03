package com.vaspap.usermanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaspap.usermanagement.dto.LoginUserRequest;
import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.util.TestData;
import com.vaspap.usermanagement.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static com.vaspap.usermanagement.model.Role.ADMIN;
import static com.vaspap.usermanagement.model.SubscriptionPlan.FREE;
import static com.vaspap.usermanagement.util.TestData.BASE_URL;
import static com.vaspap.usermanagement.util.TestData.LOGIN_URL;
import static com.vaspap.usermanagement.util.TestData.REGISTER_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static com.vaspap.usermanagement.util.MessageConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void registerUser_ShouldReturnCreated_WhenUserRegistrationIsSuccessful() throws Exception {
        RegisterUserRequest request = TestData.createValidRegisterUserRequest();

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.role").value(ADMIN.name()))
                .andExpect(jsonPath("$.subscription").value(FREE.name()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void registerUser_ShouldReturnConflict_WhenDataIntegrityViolationOccurs() throws Exception {
        RegisterUserRequest request = TestData.createValidRegisterUserRequest();

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, request)
                .andExpect(status().isCreated());

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, request)
                .andExpect(status().isConflict())
                .andExpect(content().string(DATA_INTEGRITY_VIOLATION_MESSAGE + request.username()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void registerUser_ShouldReturnBadRequest_WhenIllegalArgumentExceptionOccurs() throws Exception {
        RegisterUserRequest request = TestData.createRegisterUserRequestInvalidUsername();

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ILLEGAL_ARGUMENT_MESSAGE + request.username()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void loginUser_ShouldReturnAccepted_WhenLoginIsSuccessful() throws Exception {
        RegisterUserRequest registerUserRequest = TestData.createValidRegisterUserRequest();
        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + REGISTER_URL, registerUserRequest)
                .andExpect(status().isCreated());

        LoginUserRequest loginUserRequest = TestData.createLoginUserRequest();
        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + LOGIN_URL, loginUserRequest)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.role").value(ADMIN.name()))
                .andExpect(jsonPath("$.subscription").value(FREE.name()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void loginUser_ShouldReturnBadRequest_WhenAuthenticationFailedExceptionOccurs() throws Exception {
        LoginUserRequest request = TestData.createLoginUserRequest();

        TestUtils.performPostWithContent(mockMvc, objectMapper, BASE_URL + LOGIN_URL, request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(INVALID_USERNAME_OR_PASSWORD_MESSAGE + request.username()))
                .andDo(MockMvcResultHandlers.print());
    }
}
