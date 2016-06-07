package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.GroupDAO;
import com.naitoreivun.lop.dao.UserDAO;
import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.UserGroup;
import com.naitoreivun.lop.domain.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    public List<GroupDTO> getAll() {
        return mapToGroupDTO(groupDAO.findAll());
    }

    public Group getGroupById(Long id) {
        return groupDAO.findById(id).get();
    }

    public GroupDTO getGroupDTOById(Long id) {
        return new GroupDTO(getGroupById(id));
    }

    public List<GroupDTO> getByUserId(Long id) {
        List<UserGroup> userGroups = userDAO.findById(id).get().getUsersGroups();
        List<Group> groups = groupDAO.findByUsersGroupsIn(userGroups);
        return mapToGroupDTO(groups);
    }

    private List<GroupDTO> mapToGroupDTO(List<Group> col) {
        return col
                .stream()
                .map(GroupDTO::new)
                .sorted(Comparator.comparing(GroupDTO::getName))
                .collect(Collectors.toList());
    }
}
