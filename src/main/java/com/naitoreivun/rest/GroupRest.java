package com.naitoreivun.rest;

import com.naitoreivun.dao.GroupDAO;
import com.naitoreivun.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/group")
public class GroupRest {


    @Autowired
    private GroupDAO groupDAO;

    @RequestMapping("")
    public Set<Group> getAll() {
        return groupDAO.findAll();
    }

    @RequestMapping("/{id}")
    public Group getAll(@PathVariable Long id) {
        return groupDAO.test();
    }


}
