package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.Season;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ContestDAO extends CrudRepository<Contest, Long> {

    Set<Contest> findBySeason(Season season);

    Optional<Contest> findById(Long id);
}
