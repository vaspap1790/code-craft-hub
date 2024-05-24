package com.vaspap.usermanagement.state;

import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;

public interface SubscriptionState {
    void upgrade(User user);
    void downgrade(User user);
    SubscriptionPlan getPlan();
}
