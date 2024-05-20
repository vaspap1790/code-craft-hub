package com.vaspap.usermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionPlan subscription;

    public User() {}

    public User(String username, String password, Role role, SubscriptionPlan subscription) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.subscription = subscription;
    }

    public UserDto toUserDto() {
        return new UserDto(this.username, this.role, this.subscription);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSubscription(SubscriptionPlan subscription) {
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public SubscriptionPlan getSubscription() {
        return subscription;
    }
}
