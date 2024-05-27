package com.vaspap.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.service.UserService;
import com.vaspap.usermanagement.dto.UserDto;
import config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import util.TestData;

import static com.vaspap.usermanagement.model.Role.ADMIN;
import static com.vaspap.usermanagement.model.SubscriptionPlan.FREE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestConfig.class)
class UserControllerTest {
    private static final String BASE_URL = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void registerUser_ShouldReturnCreated_WhenUserRegistrationIsSuccessful() throws Exception {
        // given
        RegisterUserRequest request = TestData.createRegisterUserRequest();
        UserDto userDto = TestData.createUserDto();

        // when
        when(userService.registerUser(any(RegisterUserRequest.class))).thenReturn(userDto);

        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
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
        RegisterUserRequest request = TestData.createRegisterUserRequest();

        // when
        doThrow(new DataIntegrityViolationException("")).when(userService).registerUser(any(RegisterUserRequest.class));

        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
        // then
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

    @Test
    public void registerUser_ShouldReturnBadRequest_WhenIllegalArgumentExceptionOccurs() throws Exception {
        // given
        RegisterUserRequest request = TestData.createRegisterUserRequest();

        // when
        doThrow(new IllegalArgumentException("")).when(userService).registerUser(any(RegisterUserRequest.class));

        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
        // then
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

    @Test
    public void registerUser_ShouldReturnInternalServerError_WhenUnexpectedExceptionOccurs() throws Exception {
        // given
        RegisterUserRequest request = TestData.createRegisterUserRequest();

        // when
        doThrow(new RuntimeException("")).when(userService).registerUser(any(RegisterUserRequest.class));

        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
        // then
                .andExpect(status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

}