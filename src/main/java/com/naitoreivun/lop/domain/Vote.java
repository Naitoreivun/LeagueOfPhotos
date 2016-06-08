package com.naitoreivun.lop.domain;

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
    @JoinColumn(name = "image_id")
    private Image image;

    private Short rating;

    public Vote() {
    }

    public Vote(User user, Image image) {
        this(user, image, (short) 0);
    }

    public Vote(User user, Image image, Short rating) {
        this.user = user;
        this.image = image;
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }
}
