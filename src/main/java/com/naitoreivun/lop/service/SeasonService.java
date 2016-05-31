package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.SeasonDAO;
import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.Season;
import com.naitoreivun.lop.domain.dto.NewSeason;
import com.naitoreivun.lop.domain.dto.SeasonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeasonService {

    @Autowired
    private SeasonDAO seasonDAO;

    @Autowired
    private GroupService groupService;

    public void add(NewSeason newSeason) {
        Group group = groupService.getGroupById(newSeason.getGroupId());
        seasonDAO.save(new Season(newSeason, group));
    }

    public Set<SeasonDTO> getByGroupId(Long groupId) {
        Set<Season> seasons = seasonDAO.findByGroup(groupService.getGroupById(groupId));
        return seasons
                .stream()
                .map(SeasonDTO::new)
                .collect(Collectors.toSet());
    }

    public Season getSeasonById(Long id) {
        return seasonDAO.findById(id).get();
    }

    public SeasonDTO getSeasonDTOById(Long id) {
        return new SeasonDTO(getSeasonById(id));
    }
}
