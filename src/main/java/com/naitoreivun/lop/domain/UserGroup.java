package com.naitoreivun.lop.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity(name = "users_groups")
public class UserGroup implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "member_status_id")
    private MemberStatus memberStatus;

    public UserGroup() {
    }

    public UserGroup(User user, Group group, MemberStatus memberStatus) {
        this.user = user;
        this.group = group;
        this.memberStatus = memberStatus;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }
}
