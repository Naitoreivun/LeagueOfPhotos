package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.GroupDAO;
import com.naitoreivun.lop.dao.UserGroupDAO;
import com.naitoreivun.lop.domain.*;
import com.naitoreivun.lop.domain.dto.GroupDTO;
import com.naitoreivun.lop.domain.dto.NewGroup;
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
    private UserService userService;

    @Autowired
    private UserGroupDAO userGroupDAO;

    @Autowired
    private MemberStatusService memberStatusService;

    @Autowired
    private GroupTypeService groupTypeService;

    public List<GroupDTO> getAll() {
        return mapToGroupDTO(groupDAO.findAll());
    }

    public Group getGroupById(Long id) {
        return groupDAO.findById(id).get();
    }

    public GroupDTO getGroupDTOById(Long id) {
        return new GroupDTO(getGroupById(id));
    }

    public List<GroupDTO> getByUserId(Long userId) {
        List<UserGroup> userGroups = userService.getById(userId).getUsersGroups(); // TODO: 2016-06-11 zrob userGroupDAO
        List<Group> groups = groupDAO.findByUsersGroupsIn(userGroups);
        return mapToGroupDTO(groups);
    }

    public List<GroupDTO> getWithoutUser(Long userId) { // TODO: 2016-06-11 zrob userGroupDAO
        final User user = userService.getById(userId);
        List<Group> groups = groupDAO.findByUserNot(user);
        return mapToGroupDTO(groups);
    }

    private List<GroupDTO> mapToGroupDTO(List<Group> col) {
        return col
                .stream()
                .map(GroupDTO::new)
                .sorted(Comparator.comparing(GroupDTO::getName))
                .collect(Collectors.toList());
    }

    public void addUserToGroup(Long userId, Long groupId) {
        addUserToGroupWithStatus(userId, groupId, MemberStatus.MEMBER);
    }

    public void addUserToGroupWithStatus(Long userId, Long groupId, String status) {
        final Group group = getGroupById(groupId);
        addUserToGroupWithStatus(userId, group, status);
    }

    public void addUserToGroupWithStatus(Long userId, Group group, String status) {
        final User user = userService.getById(userId);
        final MemberStatus memberStatus = memberStatusService.getByStatus(status);
        UserGroup userGroup = new UserGroup(user, group, memberStatus);
        userGroupDAO.save(userGroup);
    }

    public void removeUserFromGroup(Long userId, Long groupId) {
        UserGroupPK id = new UserGroupPK(userId, groupId);
        userGroupDAO.delete(id);
    }

    public Group add(NewGroup newGroup) {
        GroupType type = groupTypeService.getByType(newGroup.getType());
        final Group group = new Group(newGroup.getName(), type, newGroup.getDescription());
        groupDAO.save(group);
        return group;
    }

    public void createGroupByUser(NewGroup newGroup, Long userId) {
        Group group = add(newGroup);
        addUserToGroupWithStatus(userId, group, MemberStatus.ADMIN);
    }
}
