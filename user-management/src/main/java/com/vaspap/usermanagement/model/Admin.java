package com.vaspap.usermanagement.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    public Admin() {
        super();
    }
}
