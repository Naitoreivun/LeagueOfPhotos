package com.naitoreivun.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "votes")
public class Vote {
    @Id
    @GeneratedValue
    private Long id;

    public Vote() {
    }
}
