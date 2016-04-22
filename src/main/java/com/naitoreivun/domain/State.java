package com.naitoreivun.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "states")
public class State {
    @Id
    @GeneratedValue
    private Long id;

    private String state;

    public State() {
    }

    public State(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
