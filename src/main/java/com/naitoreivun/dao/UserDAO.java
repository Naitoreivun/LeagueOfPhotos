package com.naitoreivun.dao;

import com.naitoreivun.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserDAO extends CrudRepository<User, Long> {

    @Override
    Set<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
