package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.Vote;
import com.naitoreivun.lop.domain.VotePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteDAO extends CrudRepository<Vote, VotePK> {
    Optional<Vote> findByUserAndImage(User user, Image image);

    @Query(value = "SELECT sum(v.rating) FROM votes v WHERE v.image = :image")
    Short getTotalRatingByImage(@Param("image") Image image);
}
