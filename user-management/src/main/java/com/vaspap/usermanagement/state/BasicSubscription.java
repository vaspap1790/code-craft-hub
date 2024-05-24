package com.vaspap.usermanagement.state;

import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;
import org.springframework.stereotype.Component;

import static com.vaspap.usermanagement.model.SubscriptionPlan.BASIC;
import static com.vaspap.usermanagement.model.SubscriptionPlan.FREE;
import static com.vaspap.usermanagement.model.SubscriptionPlan.PREMIUM;

@Component("basicSubscription")
public class BasicSubscription implements SubscriptionState {

    @Override
    public void upgrade(User user) {
        user.setSubscription(PREMIUM);
        user.setSubscriptionState(new PremiumSubscription());
    }

    @Override
    public void downgrade(User user) {
        user.setSubscription(FREE);
        user.setSubscriptionState(new FreeSubscription());
    }

    @Override
    public SubscriptionPlan getPlan() {
        return BASIC;
    }
}
