package com.naitoreivun.lop.domain.dto;

import com.naitoreivun.lop.domain.Image;

public class Score {
    private String username;
    private byte[] imageContent;
    private Short votes;

    public Score() {
    }

    public Score(String username, byte[] imageContent, Short votes) {
        this.username = username;
        this.imageContent = imageContent;
        this.votes = votes;
    }

    public Score(Image image, Short votes) {
        this(image.getUser().getUsername(), image.getContent(), votes);
    }

    public String getUsername() {
        return username;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public Short getVotes() {
        return votes;
    }
}
