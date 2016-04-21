package com.naitoreivun.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String path;

    public Image() {
    }

    public Image(String title, String path) {
        this.title = title;
        this.path = path;
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
}
