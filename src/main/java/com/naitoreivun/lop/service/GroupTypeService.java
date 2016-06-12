package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.GroupTypeDAO;
import com.naitoreivun.lop.domain.GroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupTypeService {
    @Autowired
    private GroupTypeDAO groupTypeDAO;

    public GroupType getByType(String type) {
        return groupTypeDAO.findByType(type).get();
    }
}
