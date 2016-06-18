package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.UserGroup;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupDAO extends CrudRepository<Group, Long> {

    @Override
    List<Group> findAll();

    Optional<Group> findById(Long id);

    List<Group> findByUsersGroupsIn(List<UserGroup> usersGroups);

    @Query(value = "SELECT gz FROM groups as gz WHERE gz.id NOT IN " +
            "(SELECT g.id FROM users_groups as ug inner JOIN " +
            "ug.group as g WHERE ug.user = :user)")
    List<Group> findByUserNot(@Param("user") User user);

    @Modifying
    @Query("delete from groups g where g.id = ?1")
    void delete(Long groupId);
}
