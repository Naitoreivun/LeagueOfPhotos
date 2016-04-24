package com.naitoreivun.dao;

import com.naitoreivun.domain.AppRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppRoleDAO extends CrudRepository<AppRole, Long> {

    Optional<AppRole> getByRole(String role);
}
