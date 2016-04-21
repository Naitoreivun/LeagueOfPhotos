package com.naitoreivun.domain;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "contests")
public class Contest {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @OneToMany(mappedBy = "contest")
    private Set<Image> images;

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
