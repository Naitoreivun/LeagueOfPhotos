package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.Season;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface SeasonDAO extends CrudRepository<Season, Long> {

    Set<Season> findByGroup(Group group);

    Optional<Season> findById(Long id);
}
