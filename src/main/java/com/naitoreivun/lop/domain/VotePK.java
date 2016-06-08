package com.naitoreivun.lop.domain;

import java.io.Serializable;

public class VotePK implements Serializable {
    private Long user;
    private Long image;

    public VotePK() {
    }

    public VotePK(Long user, Long image) {
        this.user = user;
        this.image = image;
    }

    public Long getUser() {
        return user;
    }

    public Long getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotePK votePK = (VotePK) o;

        if (!user.equals(votePK.user)) return false;
        return image.equals(votePK.image);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + image.hashCode();
        return result;
    }
}
