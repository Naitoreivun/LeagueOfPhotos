package com.naitoreivun.dao;

import com.naitoreivun.domain.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GroupDAO extends CrudRepository<Group, Long> {

    @Override
    Set<Group> findAll();

    @Query("select g from groups g where g.id = 1")
    Group test();
}
