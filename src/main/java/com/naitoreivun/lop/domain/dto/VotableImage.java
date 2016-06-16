package com.naitoreivun.lop.domain.dto;

import com.naitoreivun.lop.domain.Image;

public class VotableImage {
    private Long id;
    private String title;
    private byte[] content;
    private Short rating;

    public VotableImage(Long id, String title, byte[] content, Short rating) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

    public VotableImage(Image image, Short rating) {
        this(image.getId(), image.getTitle(), image.getContent(), rating);
    }

    public VotableImage(Image image) {
        this(image, (short) 0);
    }

    public VotableImage() {
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

    public Short getRating() {
        return rating;
    }
}
