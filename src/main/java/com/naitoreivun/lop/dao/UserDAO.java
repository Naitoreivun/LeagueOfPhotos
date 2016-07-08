package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.User;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    @Procedure(name = "validatePassword")
    Boolean validatePassword(@Param("password") String password, @Param("userId") Long userId);
}
