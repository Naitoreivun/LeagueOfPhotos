package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30)
    private String title;
    @Lob
    @Column(nullable = false, columnDefinition = "mediumblob")
    private byte[] content;

    @Column(nullable = false)
    private DateTime creationDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "image", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    public Image() {
    }

    public Image(String title, byte[] content, Contest contest, User user) {
        this.title = title;
        this.content = content;
        this.contest = contest;
        this.user = user;
        this.creationDate = DateTime.now();
        this.votes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getContent() {
        return content;
    }

    public Contest getContest() {
        return contest;
    }

    public User getUser() {
        return user;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }
}
