package util;

import com.vaspap.usermanagement.dto.RegisterUserRequest;
import com.vaspap.usermanagement.dto.UserDto;
import com.vaspap.usermanagement.model.Role;
import com.vaspap.usermanagement.model.SubscriptionPlan;

public class TestData {
    public static RegisterUserRequest createRegisterUserRequest() {
        return new RegisterUserRequest("username", "password", Role.ADMIN, SubscriptionPlan.FREE);
    }

    public static UserDto createUserDto() {
        return new UserDto("username", Role.ADMIN, SubscriptionPlan.FREE);
    }
}
