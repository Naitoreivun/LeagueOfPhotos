package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.UserGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupDAO extends CrudRepository<Group, Long> {

    @Override
    List<Group> findAll();

    Optional<Group> findById(Long id);

    List<Group> findByUsersGroupsIn(List<UserGroup> usersGroups);
}
