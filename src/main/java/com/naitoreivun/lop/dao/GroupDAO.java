package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.UserGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface GroupDAO extends CrudRepository<Group, Long> {

    @Override
    Set<Group> findAll();

    Optional<Group> findById(Long id);

    Set<Group> findByUsersGroupsIn(Set<UserGroup> usersGroups);
}
