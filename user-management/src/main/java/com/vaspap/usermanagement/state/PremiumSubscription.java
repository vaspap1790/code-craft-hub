package com.vaspap.usermanagement.state;

import com.vaspap.usermanagement.model.SubscriptionPlan;
import com.vaspap.usermanagement.model.User;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.vaspap.usermanagement.model.SubscriptionPlan.BASIC;
import static com.vaspap.usermanagement.model.SubscriptionPlan.PREMIUM;

@Component("premiumSubscription")
public class PremiumSubscription implements SubscriptionState {

    private final Logger LOGGER = Logger.getLogger(PremiumSubscription.class.getName());

    @Override
    public void upgrade(User user) {
        LOGGER.info(String.format("User %s is already in the highest subscription plan.", user.getUsername()));
    }

    @Override
    public void downgrade(User user) {
        user.setSubscription(BASIC);
        user.setSubscriptionState(new BasicSubscription());
    }

    @Override
    public SubscriptionPlan getPlan() {
        return PREMIUM;
    }
}
