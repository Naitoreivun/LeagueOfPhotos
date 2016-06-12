package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.UserGroup;
import com.naitoreivun.lop.domain.UserGroupPK;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupDAO extends CrudRepository<UserGroup, UserGroupPK> {
}
