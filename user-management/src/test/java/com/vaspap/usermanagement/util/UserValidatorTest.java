package com.vaspap.usermanagement.util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidatorTest {
    @Test
    public void testIsValidUsername() {
        assertTrue(UserValidator.isValidUsername("user123"));
        assertTrue(UserValidator.isValidUsername("abc"));

        assertFalse(UserValidator.isValidUsername(null));
        assertFalse(UserValidator.isValidUsername(""));
        assertFalse(UserValidator.isValidUsername("ab"));
        assertFalse(UserValidator.isValidUsername("aVeryLongUsernameExceedingLimits"));
    }

    @Test
    public void testIsValidPassword() {
        assertTrue(UserValidator.isValidPassword("password123"));
        assertTrue(UserValidator.isValidPassword("12345"));
        assertTrue(UserValidator.isValidPassword("abcde"));

        assertFalse(UserValidator.isValidPassword(null));
        assertFalse(UserValidator.isValidPassword(""));
        assertFalse(UserValidator.isValidPassword("1234"));
        assertFalse(UserValidator.isValidPassword("aVeryLongPasswordExceedingLimits"));
    }

    @Test
    public void testValidateInput_ValidInput() {
        assertDoesNotThrow(() -> UserValidator.validateInput(TestData.createValidRegisterUserRequest()));
    }

    @Test
    public void testValidateInput_InvalidUsername() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateInput(TestData.createRegisterUserRequestInvalidUsername()));
    }

    @Test
    public void testValidateInput_InvalidPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateInput(TestData.createRegisterUserRequestInvalidPassword()));
    }

    @Test
    public void testValidateInput_NullRole() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateInput(TestData.createRegisterUserRequestNullRole()));
    }

    @Test
    public void testValidateInput_NullSubscription() {
        assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validateInput(TestData.createRegisterUserRequestNullSubscription()));
    }
}