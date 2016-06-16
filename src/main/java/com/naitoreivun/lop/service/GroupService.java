package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.GroupDAO;
import com.naitoreivun.lop.dao.UserGroupDAO;
import com.naitoreivun.lop.domain.*;
import com.naitoreivun.lop.domain.dto.GroupDTO;
import com.naitoreivun.lop.domain.dto.NewGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private ImageService imageService;

    private Map<Class<?>, Function<Long, Group>> groupIdGetters = new HashMap<>();

    {
        groupIdGetters.put(Group.class, this::getGroupById);
        groupIdGetters.put(Season.class, this::getGroupBySeasonId);
        groupIdGetters.put(Contest.class, this::getGroupByContestId);
        groupIdGetters.put(Image.class, this::getGroupByImageId);
    }

    public Group getGroupBySeasonId(Long seasonId) {
        return seasonService.getSeasonById(seasonId).getGroup();
    }


    public Group getGroupByContestId(Long contestId) {
        return contestService.getContestById(contestId).getSeason().getGroup();
    }


    public Group getGroupByImageId(Long imageId) {
        return imageService.getById(imageId).getContest().getSeason().getGroup();
    }

    public boolean isMemberOfGroup(Long userId, Long id, Class<?> idClass) {
        return isMemberOfGroupWithMinStatus(userId, id, idClass, MemberStatus.MEMBER);
    }

    public boolean isMemberOfGroupWithMinStatus(Long userId, Long id, Class<?> idClass, String minStatus) {
        Long groupId = groupIdGetters.get(idClass).apply(id).getId();
        final Optional<UserGroup> one = userGroupDAO.findByUserIdAndGroupId(userId, groupId);
        if (one.isPresent()) {
            return memberStatusService.hasMinStatus(one.get(), minStatus);
        }
        return false;
    }

    public boolean isMemberOfGroupWithMinStatus(Long userId, Long groupId, String minStatus) {
        final Optional<UserGroup> one = userGroupDAO.findByUserIdAndGroupId(userId, groupId);
        if (one.isPresent()) {
            return memberStatusService.hasMinStatus(one.get(), minStatus);
        }
        return false;
    }

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
        userGroups = userGroups
                .stream()
                .filter(memberStatusService::isAcceptedUser)
                .collect(Collectors.toList());
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

    public void updateGroup(Long groupId, NewGroup newGroup) {
        Group group = getGroupById(groupId);
        GroupType groupType = groupTypeService.getByType(newGroup.getType());
        group.setNewDetails(newGroup.getName(), newGroup.getDescription(), groupType);
        groupDAO.save(group);
    }

    public void removeGroup(Long groupId) {
        groupDAO.delete(groupId);
    }
}
