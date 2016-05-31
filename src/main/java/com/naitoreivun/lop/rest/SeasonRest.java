package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.dto.NewSeason;
import com.naitoreivun.lop.domain.dto.SeasonDTO;
import com.naitoreivun.lop.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/seasons")
public class SeasonRest {

    @Autowired
    private SeasonService seasonService;

    @RequestMapping(value = "/{seasonId}")
    public ResponseEntity<SeasonDTO> getById(@PathVariable Long seasonId) {
        return new ResponseEntity<>(seasonService.getSeasonDTOById(seasonId), HttpStatus.OK);
    }

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<Set<SeasonDTO>> getByGroupId(@PathVariable Long groupId) {
        Set<SeasonDTO> seasons = seasonService.getByGroupId(groupId);
        return new ResponseEntity<>(seasons, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody NewSeason newSeason) {
        seasonService.add(newSeason);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
