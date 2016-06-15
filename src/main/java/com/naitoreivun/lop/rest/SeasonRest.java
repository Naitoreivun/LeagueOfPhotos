package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.MemberStatus;
import com.naitoreivun.lop.domain.Season;
import com.naitoreivun.lop.domain.dto.NewSeason;
import com.naitoreivun.lop.domain.dto.SeasonDTO;
import com.naitoreivun.lop.security.ForGroupMember;
import com.naitoreivun.lop.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/seasons")
public class SeasonRest {

    @Autowired
    private SeasonService seasonService;

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Season.class)
    @RequestMapping(value = "/{seasonId}")
    public ResponseEntity<SeasonDTO> getById(final HttpServletRequest request,
                                             @PathVariable Long seasonId) {
        return new ResponseEntity<>(seasonService.getSeasonDTOById(seasonId), HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Season.class, minStatus = MemberStatus.MODERATOR)
    @RequestMapping(value = "/{seasonId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSeason(final HttpServletRequest request,
                                          @PathVariable Long seasonId,
                                          @RequestBody NewSeason newSeason) {
        seasonService.updateSeason(seasonId, newSeason);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class)
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<List<SeasonDTO>> getByGroupId(final HttpServletRequest request,
                                                        @PathVariable Long groupId) {
        List<SeasonDTO> seasons = seasonService.getByGroupId(groupId);
        return new ResponseEntity<>(seasons, HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Group.class, minStatus = MemberStatus.MODERATOR)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(final HttpServletRequest request,
                                 @RequestParam Long groupId,
                                 @RequestBody NewSeason newSeason) {
        seasonService.add(newSeason, groupId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
