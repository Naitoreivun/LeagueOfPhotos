package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.dto.ContestDTO;
import com.naitoreivun.lop.domain.dto.NewContest;
import com.naitoreivun.lop.domain.dto.Score;
import com.naitoreivun.lop.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contests")
public class ContestRest {

    @Autowired
    private ContestService contestService;

    @RequestMapping(value = "/{contestId}")
    public ResponseEntity<ContestDTO> getById(@PathVariable Long contestId) {
        return new ResponseEntity<>(contestService.getContestDTOById(contestId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{contestId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateContest(@PathVariable Long contestId, @RequestBody NewContest newContest) {
        contestService.updateContest(contestId, newContest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/season/{seasonId}", method = RequestMethod.GET)
    public ResponseEntity<List<ContestDTO>> getByGroupId(@PathVariable Long seasonId) {
        List<ContestDTO> contests = contestService.getBySeasonId(seasonId);
        return new ResponseEntity<>(contests, HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody NewContest newContest) {
        contestService.add(newContest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{contestId}/scores")
    public ResponseEntity<List<Score>> getScoresByContestId(@PathVariable Long contestId) {
        return new ResponseEntity<>(contestService.getScoresByContestId(contestId), HttpStatus.OK);
    }

}
