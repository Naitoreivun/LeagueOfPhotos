package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.UserDAO;
import com.naitoreivun.lop.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User getById(Long id) {
        return userDAO.findById(id).get(); // TODO: 2016-06-02 handle null
    }
}
