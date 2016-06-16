package com.naitoreivun.lop.domain;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "votes")
@IdClass(VotePK.class)
public class Vote implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Column(nullable = false)
    private Short rating;

    @Column(nullable = false)
    private DateTime creationDate;

    public Vote() {
    }

    public Vote(User user, Image image) {
        this(user, image, (short) 0);
    }

    public Vote(User user, Image image, Short rating) {
        this.user = user;
        this.image = image;
        this.rating = rating;
        this.creationDate = DateTime.now();
    }

    public User getUser() {
        return user;
    }

    public Image getImage() {
        return image;
    }

    public Short getRating() {
        return rating;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }
}
