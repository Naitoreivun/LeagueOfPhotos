package com.naitoreivun.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String path;

    private DateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private Set<Vote> votes;

    public Image() {
    }

    public Image(String title, String path, Contest contest, User user) {
        this.title = title;
        this.path = path;
        this.contest = contest;
        this.user = user;
        this.creationDate = DateTime.now();
        this.votes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public Contest getContest() {
        return contest;
    }

    public User getUser() {
        return user;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }
}
