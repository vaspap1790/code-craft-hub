package com.vaspap.usermanagement.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

import static com.vaspap.usermanagement.model.SubscriptionPlan.BASIC;
import static com.vaspap.usermanagement.model.SubscriptionPlan.FREE;
import static com.vaspap.usermanagement.model.SubscriptionPlan.PREMIUM;

@Component
public class SubscriptionStateFactory {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionStateFactory.class.getName());

    private final Map<String, SubscriptionState> subscriptionStateMap;

    @Autowired
    public SubscriptionStateFactory(@Qualifier("basicSubscription") SubscriptionState basicSubscription,
                                    @Qualifier("freeSubscription") SubscriptionState freeSubscription,
                                    @Qualifier("premiumSubscription") SubscriptionState premiumSubscription){
        subscriptionStateMap = Map.of(
                FREE.name(), freeSubscription,
                BASIC.name(), basicSubscription,
                PREMIUM.name(), premiumSubscription
        );
    }

    public SubscriptionState getState(String plan){
        SubscriptionState state = subscriptionStateMap.get(plan);
        if (state == null) {
            LOGGER.severe("Unknown subscription plan: " + plan);
            throw new IllegalArgumentException("Unknown subscription plan: " + plan);
        }
        return state;
    }
}
