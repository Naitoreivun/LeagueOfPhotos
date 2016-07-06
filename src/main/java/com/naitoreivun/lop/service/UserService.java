package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.UserDAO;
import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.dto.UserInGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberStatusService memberStatusService;

    public User getById(Long id) {
        return userDAO.findById(id).get(); // TODO: 2016-06-02 handle null
    }

    public List<UserInGroup> getAcceptedUsersByGroupId(Long groupId) {
        final Group group = groupService.getGroupById(groupId);

        return group.getUsersGroups()
                .stream()
                .filter(memberStatusService::isAcceptedUser)
                .map(UserInGroup::new)
                .sorted(Comparator.comparing(UserInGroup::getUsername))
                .collect(Collectors.toList());
    }

    public List<UserInGroup> getRequestersByGroupId(Long groupId) {
        final Group group = groupService.getGroupById(groupId);

        return group.getUsersGroups()
                .stream()
                .filter(memberStatusService::isRequester)
                .map(UserInGroup::new)
                .sorted(Comparator.comparing(UserInGroup::getUsername))
                .collect(Collectors.toList());
    }
}
