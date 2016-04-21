package com.naitoreivun.dao;

import com.naitoreivun.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserDAO extends CrudRepository<User, Long> {

    @Query("select u from users u where u.username = ?1")
    Optional<User> xd(String username);

//    @Query("SELECT u.id from users u JOIN users_groups ug JOIN groups g WHERE g.id = 2")
//    Set<User> gimmy();
}
