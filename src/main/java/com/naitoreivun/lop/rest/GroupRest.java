package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.MemberStatus;
import com.naitoreivun.lop.domain.dto.GroupDTO;
import com.naitoreivun.lop.domain.dto.NewGroup;
import com.naitoreivun.lop.domain.dto.UserInGroup;
import com.naitoreivun.lop.security.ClaimGetter;
import com.naitoreivun.lop.security.ForGroupMember;
import com.naitoreivun.lop.service.GroupService;
import com.naitoreivun.lop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/groups")
public class GroupRest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;


    @RequestMapping("")
    public ResponseEntity<List<GroupDTO>> getAll() {
        return new ResponseEntity<>(groupService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> create(final HttpServletRequest request,
                                    @RequestBody NewGroup newGroup) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        groupService.createGroupByUser(newGroup, currentUserId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class)
    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<GroupDTO> getById(final HttpServletRequest request, @PathVariable Long groupId) {
        return new ResponseEntity<>(groupService.getGroupDTOById(groupId), HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class, minStatus = MemberStatus.MODERATOR)
    @RequestMapping(value = "/{groupId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateGroup(final HttpServletRequest request,
                                         @PathVariable Long groupId, @RequestBody NewGroup newGroup) {
        groupService.updateGroup(groupId, newGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class, minStatus = MemberStatus.ADMIN)
    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeGroup(final HttpServletRequest request, @PathVariable Long groupId) {
        groupService.removeGroup(groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{groupId}/users/current", method = RequestMethod.POST)
    public ResponseEntity<?> addCurrentUserToGroup(final HttpServletRequest request,
                                                   @PathVariable Long groupId) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        groupService.addUserToGroup(currentUserId, groupId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{groupId}/users/current", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeCurrentUserFromGroup(final HttpServletRequest request,
                                                        @PathVariable Long groupId) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        groupService.removeUserFromGroup(currentUserId, groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class, minStatus = MemberStatus.ADMIN)
    @RequestMapping(value = "/{groupId}/users/{userId}/status/{newStatus}", method = RequestMethod.PUT)
    public ResponseEntity<?> appoint(final HttpServletRequest request,
                                     @PathVariable Long groupId,
                                     @PathVariable Long userId,
                                     @PathVariable String newStatus) throws ServletException {

        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        if (!currentUserId.equals(userId)) {
            groupService.updateUserMemberStatusInGroup(groupId, userId, newStatus);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class)
    @RequestMapping(value = "/{groupId}/users/current/status/moderator", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isCurrentUserGroupModerator(final HttpServletRequest request,
                                                               @PathVariable Long groupId) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        boolean result = groupService.isMemberOfGroupWithMinStatus(currentUserId, groupId, MemberStatus.MODERATOR);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class)
    @RequestMapping(value = "/{groupId}/users/current/status/admin", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isCurrentUserGroupAdmin(final HttpServletRequest request,
                                                           @PathVariable Long groupId) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        boolean result = groupService.isMemberOfGroupWithMinStatus(currentUserId, groupId, MemberStatus.ADMIN);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class)
    @RequestMapping(value = "/{groupId}/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserInGroup>> getAcceptedUsersByGroupId(final HttpServletRequest request,
                                                                       @PathVariable Long groupId) {
        return new ResponseEntity<>(userService.getAcceptedUsersByGroupId(groupId), HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class, minStatus = MemberStatus.MODERATOR)
    @RequestMapping(value = "/{groupId}/requesters", method = RequestMethod.GET)
    public ResponseEntity<List<UserInGroup>> getRequestersByGroupId(final HttpServletRequest request,
                                                                    @PathVariable Long groupId) {
        return new ResponseEntity<>(userService.getRequestersByGroupId(groupId), HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class, minStatus = MemberStatus.MODERATOR)
    @RequestMapping(value = "/{groupId}/requesters/{requesterId}/accept", method = RequestMethod.PUT)
    public ResponseEntity<?> acceptRequester(final HttpServletRequest request,
                                             @PathVariable Long groupId,
                                             @PathVariable Long requesterId) {
        groupService.acceptRequester(groupId, requesterId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class, minStatus = MemberStatus.MODERATOR)
    @RequestMapping(value = "/{groupId}/requesters/{requesterId}/reject", method = RequestMethod.PUT)
    public ResponseEntity<?> rejectRequester(final HttpServletRequest request,
                                             @PathVariable Long groupId,
                                             @PathVariable Long requesterId) {
        groupService.rejectRequester(groupId, requesterId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)  // TODO: only for admin_role?
    public ResponseEntity<List<GroupDTO>> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(groupService.getByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getByCurrentUser(final HttpServletRequest request) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        return new ResponseEntity<>(groupService.getByUserId(currentUserId), HttpStatus.OK);
    }

    @RequestMapping(value = "/withoutuser/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getWithoutUser(@PathVariable Long userId) { // TODO: only for admin_role?
        return new ResponseEntity<>(groupService.getWithoutUser(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/withoutuser/current", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getWithoutCurrentUser(final HttpServletRequest request) throws
            ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        return new ResponseEntity<>(groupService.getWithoutUser(currentUserId), HttpStatus.OK);
    }
}
