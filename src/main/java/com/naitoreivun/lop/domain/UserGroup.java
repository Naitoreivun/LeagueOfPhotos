package com.naitoreivun.lop.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "users_groups")
@IdClass(UserGroupPK.class)
public class UserGroup implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "member_status_id", nullable = false)
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

    public void setMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }
}
