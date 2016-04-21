package com.naitoreivun.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "member_status")
public class MemberStatus {
    @Id
    @GeneratedValue
    private Long id;

    private String status;

    @OneToMany(mappedBy = "memberStatus")
    private Set<UserGroup> userGroup;

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

    public Set<UserGroup> getUserGroup() {
        return userGroup;
    }
}
