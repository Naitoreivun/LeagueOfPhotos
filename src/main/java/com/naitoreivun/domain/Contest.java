package com.naitoreivun.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "contests")
public class Contest {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    // TODO: 2016-04-21 data_rozp, data_zak, dak_zak_glosowania, data_stworzenia


    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @JsonIgnore
    @OneToMany(mappedBy = "contest")
    private Set<Image> images;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    public Contest() {
    }

    public Contest(String name, Season season) {
        this(name, season, "");
    }

    public Contest(String name, Season season, String description) {
        this.name = name;
        this.season = season;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public Set<Image> getImages() {
        return images;
    }

    public State getState() {
        return state;
    }
}
