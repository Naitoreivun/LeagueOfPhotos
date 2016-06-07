package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
