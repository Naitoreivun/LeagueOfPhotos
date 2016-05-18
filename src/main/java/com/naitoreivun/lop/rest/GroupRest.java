package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.Group;
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
    public ResponseEntity<Set<Group>> getAll() {
        return new ResponseEntity<>(groupService.getAll(), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> getById(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}")
    public ResponseEntity<Set<Group>> getByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.getByUserId(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/current")
    public ResponseEntity<Set<Group>> getByCurrentUser(final HttpServletRequest request) throws ServletException {
        final Claims claims = (Claims) request.getAttribute("claims");
        Long currentUserId = ((Integer) claims.get("id")).longValue();
        return new ResponseEntity<>(groupService.getByUserId(currentUserId), HttpStatus.OK);
    }
}
