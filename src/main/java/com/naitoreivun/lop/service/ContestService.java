package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.ContestDAO;
import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.Season;
import com.naitoreivun.lop.domain.dto.ContestDTO;
import com.naitoreivun.lop.domain.dto.NewContest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContestService {

    @Autowired
    ContestDAO contestDAO;

    @Autowired
    private SeasonService seasonService;


    public Set<ContestDTO> getBySeasonId(Long seasonId) {
        Set<Contest> contests = contestDAO.findBySeason(seasonService.getSeasonById(seasonId));
        return contests
                .stream()
                .map(ContestDTO::new)
                .collect(Collectors.toSet());
    }

    public void add(NewContest newContest) {
        Season season = seasonService.getSeasonById(newContest.getSeasonId());
        contestDAO.save(new Contest(newContest, season));
    }
}
