package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.dto.ContestDTO;
import com.naitoreivun.lop.domain.dto.NewContest;
import com.naitoreivun.lop.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/contests")
public class ContestRest {

    @Autowired
    ContestService contestService;

    @RequestMapping(value = "/season/{seasonId}", method = RequestMethod.GET)
    public ResponseEntity<Set<ContestDTO>> getByGroupId(@PathVariable Long seasonId) {
        Set<ContestDTO> contests = contestService.getBySeasonId(seasonId);
        return new ResponseEntity<>(contests, HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody NewContest newContest) {
        contestService.add(newContest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
