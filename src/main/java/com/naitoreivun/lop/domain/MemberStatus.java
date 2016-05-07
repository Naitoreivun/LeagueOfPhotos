package com.naitoreivun.lop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "member_status")
public class MemberStatus {
    @Id
    @GeneratedValue
    private Long id;

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
