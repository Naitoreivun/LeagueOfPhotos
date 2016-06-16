package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.MemberStatus;
import com.naitoreivun.lop.domain.dto.ContestDTO;
import com.naitoreivun.lop.domain.dto.NewContest;
import com.naitoreivun.lop.domain.dto.Score;
import com.naitoreivun.lop.security.ForGroupMember;
import com.naitoreivun.lop.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/contests")
public class ContestRest {

    @Autowired
    private ContestService contestService;

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Contest.class)
    @RequestMapping(value = "/{contestId}")
    public ResponseEntity<ContestDTO> getById(final HttpServletRequest request,
                                              @PathVariable Long contestId) {
        return new ResponseEntity<>(contestService.getContestDTOById(contestId), HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Contest.class, minStatus = MemberStatus.MODERATOR)
    @RequestMapping(value = "/{contestId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateContest(final HttpServletRequest request,
                                           @PathVariable Long contestId,
                                           @RequestBody NewContest newContest) {
        contestService.updateContest(contestId, newContest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Contest.class, minStatus = MemberStatus.MODERATOR)
    @RequestMapping(value = "/{contestId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeContest(final HttpServletRequest request, @PathVariable Long contestId) {
        contestService.removeContest(contestId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Contest.class)
    @RequestMapping(value = "/season/{seasonId}", method = RequestMethod.GET)
    public ResponseEntity<List<ContestDTO>> getBySeasonId(final HttpServletRequest request,
                                                          @PathVariable Long seasonId) {
        List<ContestDTO> contests = contestService.getBySeasonId(seasonId);
        return new ResponseEntity<>(contests, HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.POST) //todo jak to zwalidowac?
    public ResponseEntity<?> add(final HttpServletRequest request,
                                 @RequestParam Long seasonId,
                                 @RequestBody NewContest newContest) {
        contestService.add(newContest, seasonId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ForGroupMember(requestNr = 0, idNr = 1, idClass = Contest.class)
    @RequestMapping(value = "/{contestId}/scores")
    public ResponseEntity<List<Score>> getScoresByContestId(final HttpServletRequest request,
                                                            @PathVariable Long contestId) {
        return new ResponseEntity<>(contestService.getScoresByContestId(contestId), HttpStatus.OK);
    }

}
