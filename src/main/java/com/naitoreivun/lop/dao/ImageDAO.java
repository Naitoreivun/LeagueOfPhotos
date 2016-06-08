package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ImageDAO extends CrudRepository<Image, Long> {

    Optional<Image> findByContestAndUser(Contest contest, User user);

    List<Image> findByContestAndUserNot(Contest contest, User user);

    Optional<Image> findById(Long id);

    List<Image> findByContest(Contest contest);
}
