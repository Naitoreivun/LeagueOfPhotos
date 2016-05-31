package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.GroupDAO;
import com.naitoreivun.lop.dao.UserDAO;
import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.UserGroup;
import com.naitoreivun.lop.domain.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    public Set<GroupDTO> getAll() {
        return mapToGroupDTO(groupDAO.findAll());
    }

    public Group getGroupById(Long id) {
        return groupDAO.findById(id).get();
    }

    public GroupDTO getGroupDTOById(Long id) {
        return new GroupDTO(getGroupById(id));
    }

    public Set<GroupDTO> getByUserId(Long id) {
        Set<UserGroup> userGroups = userDAO.findById(id).get().getUsersGroups();
        Set<Group> groups = groupDAO.findByUsersGroupsIn(userGroups);
        return mapToGroupDTO(groups);
    }

    private Set<GroupDTO> mapToGroupDTO(Set<Group> col) {
        return col
                .stream()
                .map(GroupDTO::new)
                .collect(Collectors.toSet());
    }
}
