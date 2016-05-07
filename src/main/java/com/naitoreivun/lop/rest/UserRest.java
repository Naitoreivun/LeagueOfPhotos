package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.dao.AppRoleDAO;
import com.naitoreivun.lop.dao.UserDAO;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.dto.SignupForm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getById(@PathVariable Long id){
        User user = userDAO.findById(id).get(); //TODO exception if null
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody SignupForm signupForm) {
//        if(!signupForm.isValid()) { } // TODO: 2016-04-24 exception

        User user = new User(signupForm, appRoleDAO.getByRole("ROLE_USER").get());
        userDAO.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
