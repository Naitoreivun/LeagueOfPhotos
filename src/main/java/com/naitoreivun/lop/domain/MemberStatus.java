package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "member_status")
public class MemberStatus {
    @Transient
    @JsonIgnore
    public static final String MEMBER = "MEMBER";
    @Transient
    @JsonIgnore
    public static final String ADMIN = "ADMIN";
    @Transient
    @JsonIgnore
    public static final String MODERATOR = "MODERATOR";
    @Transient
    @JsonIgnore
    public static final String BANNED = "BANNED";
    @Transient
    @JsonIgnore
    public static final String LEAVER = "LEAVER";
    @Transient
    @JsonIgnore
    public static final String REQUESTER = "REQUESTER";

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String status;

    public MemberStatus() {
    }

    public MemberStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
