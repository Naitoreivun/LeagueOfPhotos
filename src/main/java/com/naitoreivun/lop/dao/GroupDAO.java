package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GroupDAO extends CrudRepository<Group, Long> {

    @Override
    Set<Group> findAll();
}
