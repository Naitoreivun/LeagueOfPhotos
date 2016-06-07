package com.naitoreivun.lop.domain.dto;

import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.UserGroup;

public class UserInGroup {
    private Long id;
    private String username;
    private String memberStatus;

    public UserInGroup(Long id, String username, String memberStatus) {
        this.id = id;
        this.username = username;
        this.memberStatus = memberStatus;
    }

    public UserInGroup(User user, String memberStatus) {
        this(user.getId(), user.getUsername(), memberStatus);
    }

    public UserInGroup(UserGroup userGroup) {
        this(userGroup.getUser(), userGroup.getMemberStatus().getStatus());
    }

    public UserInGroup() {
    }

    public String getUsername() {
        return username;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public Long getId() {
        return id;
    }
}
