package com.naitoreivun.domain;

import javax.persistence.*;

@Entity(name = "contests")
public class Contest {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    public Contest() {
    }

    public Contest(String name, Season season) {
        this.name = name;
        this.season = season;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }
}
