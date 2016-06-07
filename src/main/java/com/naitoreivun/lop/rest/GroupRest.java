package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.dto.GroupDTO;
import com.naitoreivun.lop.domain.dto.UserInGroup;
import com.naitoreivun.lop.security.ClaimGetter;
import com.naitoreivun.lop.service.GroupService;
import com.naitoreivun.lop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


    @RequestMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDTO> getById(@PathVariable Long groupId) {
        return new ResponseEntity<>(groupService.getGroupDTOById(groupId), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.getByUserId(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    public ResponseEntity<List<GroupDTO>> getByCurrentUser(final HttpServletRequest request) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        return new ResponseEntity<>(groupService.getByUserId(currentUserId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{groupId}/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserInGroup>> getAcceptedUsersByGroupId(@PathVariable Long groupId) {
        return new ResponseEntity<>(userService.getAcceptedUsersByGroupId(groupId), HttpStatus.OK);
    }
}
