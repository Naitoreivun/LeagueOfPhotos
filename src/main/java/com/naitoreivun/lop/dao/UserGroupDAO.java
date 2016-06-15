package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.UserGroup;
import com.naitoreivun.lop.domain.UserGroupPK;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserGroupDAO extends CrudRepository<UserGroup, UserGroupPK> {
    Optional<UserGroup> findByUserAndGroup(User user, Group group);

    Optional<UserGroup> findByUserIdAndGroupId(Long userId, Long groupId);
}
