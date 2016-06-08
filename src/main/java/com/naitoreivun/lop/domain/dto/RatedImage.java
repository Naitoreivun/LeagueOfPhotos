package com.naitoreivun.lop.domain.dto;

public class RatedImage {
    private Long id;
    private Short rating;

    public RatedImage(Long id, Short rating) {
        this.id = id;
        this.rating = rating;
    }

    public RatedImage() {
    }

    public Long getId() {
        return id;
    }

    public Short getRating() {
        return rating;
    }
}
