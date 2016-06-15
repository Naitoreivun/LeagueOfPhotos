package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.ContestDAO;
import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.domain.Season;
import com.naitoreivun.lop.domain.dto.ContestDTO;
import com.naitoreivun.lop.domain.dto.NewContest;
import com.naitoreivun.lop.domain.dto.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContestService {

    @Autowired
    ContestDAO contestDAO;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private ImageService imageService;
    @Autowired
    private VoteService voteService;


    public List<ContestDTO> getBySeasonId(Long seasonId) {
        List<Contest> contests = contestDAO.findBySeason(seasonService.getSeasonById(seasonId));
        return contests
                .stream()
                .map(ContestDTO::new)
                .sorted(Comparator.comparing(ContestDTO::getName))
                .collect(Collectors.toList());
    }

    public void add(NewContest newContest, Long seasonId) {
        Season season = seasonService.getSeasonById(seasonId);
        contestDAO.save(new Contest(newContest, season));
    }

    public Contest getContestById(Long contestId) {
        return contestDAO.findById(contestId).get(); // TODO: 2016-06-02 hande null
    }

    public ContestDTO getContestDTOById(Long contestId) {
        return new ContestDTO(getContestById(contestId));
    }

    public List<Score> getScoresByContestId(Long contestId) {
        List<Image> images = imageService.getByContestId(contestId);
        return images
                .stream()
                .map(image -> new Score(image, voteService.getTotalRatingByImage(image)))
                .sorted(Comparator.comparing(Score::getVotes).reversed())
                .collect(Collectors.toList());
    }

    public void updateContest(Long contestId, NewContest newContest) {
        Contest contest = getContestById(contestId);
        contest.setNewDetails(newContest);
        contestDAO.save(contest);
    }
}
