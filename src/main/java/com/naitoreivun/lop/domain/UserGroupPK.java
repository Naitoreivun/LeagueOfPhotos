package com.naitoreivun.lop.domain;

import java.io.Serializable;

public class UserGroupPK implements Serializable {
    private Long user;
    private Long group;

    public UserGroupPK() {
    }

    public UserGroupPK(Long user, Long group) {
        this.user = user;
        this.group = group;
    }

    public Long getUser() {
        return user;
    }

    public Long getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupPK that = (UserGroupPK) o;

        if (!user.equals(that.user)) return false;
        return group.equals(that.group);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + group.hashCode();
        return result;
    }
}
