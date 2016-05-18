package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.GroupDAO;
import com.naitoreivun.lop.dao.UserDAO;
import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    public Set<Group> getAll() {
        return groupDAO.findAll();
    }

    public Group getById(Long id) {
        return groupDAO.findById(id).get();
    }

    public Set<Group> getByUserId(Long id) {
        Set<UserGroup> userGroups = userDAO.findById(id).get().getUsersGroups();
        return groupDAO.findByUsersGroupsIn(userGroups);
    }
}
