package com.naitoreivun.rest;

import com.naitoreivun.dao.AppRoleDAO;
import com.naitoreivun.dao.UserDAO;
import com.naitoreivun.domain.User;
import com.naitoreivun.domain.dto.SignupForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserRest {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AppRoleDAO appRoleDAO;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Set<User>> getAll() {
        Set<User> users = userDAO.findAll();
        return new ResponseEntity<Set<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getById(@PathVariable Long id){
        User user = userDAO.findById(id).get(); //TODO exception if null
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody SignupForm signupForm) {
//        if(!signupForm.isValid()) { } // TODO: 2016-04-24 exception

        User user = new User(signupForm, appRoleDAO.getByRole("ROLE_USER").get());
        userDAO.save(user);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody SignupForm loginForm) {
//        if(!signupForm.isValid()) { } // TODO: 2016-04-24 exception

//        User user = new User(loginForm, appRoleDAO.getByRole("ROLE_USER").get());
//        userDAO.save(user);
//        return new ResponseEntity<User>(user, HttpStatus.CREATED);
        return null;
    }
}
