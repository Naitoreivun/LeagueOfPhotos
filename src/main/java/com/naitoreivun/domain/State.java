package com.naitoreivun.domain;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "states")
public class State {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    @JoinColumn(name = "state_id")
    private Set<Contest> contests;

    @OneToMany
    @JoinColumn(name = "state_id")
    private Set<Season> seasons;


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

    public Set<Contest> getContests() {
        return contests;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }
}
