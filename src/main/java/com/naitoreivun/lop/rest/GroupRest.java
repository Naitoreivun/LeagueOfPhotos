package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.dto.GroupDTO;
import com.naitoreivun.lop.security.ClaimGetter;
import com.naitoreivun.lop.service.GroupService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupRest {

    @Autowired
    private GroupService groupService;

    @RequestMapping("")
    public ResponseEntity<Set<GroupDTO>> getAll() {
        return new ResponseEntity<>(groupService.getAll(), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.getGroupDTOById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}")
    public ResponseEntity<Set<GroupDTO>> getByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.getByUserId(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/current")
    public ResponseEntity<Set<GroupDTO>> getByCurrentUser(final HttpServletRequest request) throws ServletException {
        Long currentUserId = ClaimGetter.getCurrentUserId(request);
        return new ResponseEntity<>(groupService.getByUserId(currentUserId), HttpStatus.OK);
    }
}
