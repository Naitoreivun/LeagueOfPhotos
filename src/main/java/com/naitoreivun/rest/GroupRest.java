package com.naitoreivun.rest;

import com.naitoreivun.dao.GroupDAO;
import com.naitoreivun.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
