package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.Season;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContestDAO extends CrudRepository<Contest, Long> {

    List<Contest> findBySeason(Season season);

    Optional<Contest> findById(Long id);
}
