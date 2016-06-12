package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.dto.GroupDTO;
import com.naitoreivun.lop.domain.dto.NewGroup;
import com.naitoreivun.lop.domain.dto.UserInGroup;
import com.naitoreivun.lop.security.ClaimGetter;
import com.naitoreivun.lop.service.GroupService;
import com.naitoreivun.lop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<?> create(@RequestBody NewGroup newGroup,
                                 final HttpServletRequest request) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        groupService.createGroupByUser(newGroup, currentUserId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDTO> getById(@PathVariable Long groupId) {
        return new ResponseEntity<>(groupService.getGroupDTOById(groupId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{groupId}/users/current", method = RequestMethod.POST)
    public ResponseEntity<?> addCurrentUserToGroup(@PathVariable Long groupId,
                                            final HttpServletRequest request) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        groupService.addUserToGroup(currentUserId, groupId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{groupId}/users/current", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeCurrentUserFromGroup(@PathVariable Long groupId,
                                            final HttpServletRequest request) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        groupService.removeUserFromGroup(currentUserId, groupId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{groupId}/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserInGroup>> getAcceptedUsersByGroupId(@PathVariable Long groupId) {
        return new ResponseEntity<>(userService.getAcceptedUsersByGroupId(groupId), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(groupService.getByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getByCurrentUser(final HttpServletRequest request) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        return new ResponseEntity<>(groupService.getByUserId(currentUserId), HttpStatus.OK);
    }

    @RequestMapping(value = "/withoutuser/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getWithoutUser(@PathVariable Long userId) {
        return new ResponseEntity<>(groupService.getWithoutUser(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/withoutuser/current", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getWithoutCurrentUser(final HttpServletRequest request) throws
            ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        return new ResponseEntity<>(groupService.getWithoutUser(currentUserId), HttpStatus.OK);
    }
}
