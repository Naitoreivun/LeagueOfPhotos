package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.dao.GroupDAO;
import com.naitoreivun.lop.domain.Group;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupRest {


    @Autowired
    private GroupDAO groupDAO;

    @RequestMapping("")
    public Set<Group> getAll() {
        return groupDAO.findAll();
    }


    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group getAll(@PathVariable Long id) {
        return groupDAO.findOne(id);
    }


}
