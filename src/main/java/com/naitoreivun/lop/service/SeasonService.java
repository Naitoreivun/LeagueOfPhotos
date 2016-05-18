package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.SeasonDAO;
import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.Season;
import com.naitoreivun.lop.domain.dto.NewSeason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonService {

    @Autowired
    private SeasonDAO seasonDAO;

    @Autowired
    private GroupService groupService;

    public void add(NewSeason newSeason, Long groupId) {
        Group group = groupService.getById(groupId);
        seasonDAO.save(new Season(newSeason, group));
    }
}
