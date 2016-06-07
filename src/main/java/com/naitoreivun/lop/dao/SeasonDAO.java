package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.Season;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SeasonDAO extends CrudRepository<Season, Long> {

    List<Season> findByGroup(Group group);

    Optional<Season> findById(Long id);
}
