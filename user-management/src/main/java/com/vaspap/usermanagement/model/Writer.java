package com.vaspap.usermanagement.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("WRITER")
public class Writer extends User{
    public Writer() {
        super();
    }
}
