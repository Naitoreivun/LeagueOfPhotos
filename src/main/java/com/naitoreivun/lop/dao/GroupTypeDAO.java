package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.GroupType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupTypeDAO extends CrudRepository<GroupType, Long> {

    Optional<GroupType> findByType(String type);
}
