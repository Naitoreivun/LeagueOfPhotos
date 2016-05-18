package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.dto.NewSeason;
import com.naitoreivun.lop.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/seasons")
public class SeasonRest {

    @Autowired
    private SeasonService seasonService;

    private class Lol {
        public String text;
        public Lol(String text) {
            this.text = text;
        }
    }

    @RequestMapping(value = "/lol", method = RequestMethod.GET)
    public ResponseEntity<Lol> getLol(@PathVariable Long groupId) {
        return new ResponseEntity<>(new Lol("lol"), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(@PathVariable Long groupId, @RequestBody NewSeason newSeason) {
        seasonService.add(newSeason, groupId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
