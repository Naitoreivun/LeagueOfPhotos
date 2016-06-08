package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.domain.VotableImage;
import com.naitoreivun.lop.domain.dto.RatedImage;
import com.naitoreivun.lop.security.ClaimGetter;
import com.naitoreivun.lop.service.ImageService;
import com.naitoreivun.lop.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageRest {

    @Autowired
    private ImageService imageService;

    @Autowired
    private VoteService voteService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody MultipartFile file,
                                 @RequestParam("title") String title,
                                 @RequestParam("contestId") Long contestId,
                                 final HttpServletRequest request) throws ServletException {
        HttpStatus status;
        if (imageService.add(file, title, contestId, ClaimGetter.getCurrentUserId(request))) {
            status = HttpStatus.CREATED;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @RequestMapping(value = "/contest/{contestId}/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Image> getByContestIdAndUserId(@PathVariable Long contestId,
                                                         @PathVariable Long userId) {
        Image image = imageService.getByContestIdAndUserId(contestId, userId);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @RequestMapping(value = "/contest/{contestId}/currentUser", method = RequestMethod.GET)
    public ResponseEntity<Image> getByContestIdAndUserId(@PathVariable Long contestId,
                                                         final HttpServletRequest request) throws ServletException {
        final Long currentUserId = ClaimGetter.getCurrentUserId(request);
        Image image = imageService.getByContestIdAndUserId(contestId, currentUserId);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @RequestMapping(value = "/contest/{contestId}/votable", method = RequestMethod.GET)
    public ResponseEntity<List<VotableImage>> getVotableImagesByContestId(@PathVariable Long contestId,
                                                                          final HttpServletRequest request) throws ServletException {
        final Long currentUserId = ClaimGetter.getCurrentUserId(request);
        List<VotableImage> images = imageService.getVotableImagesByContestId(contestId, currentUserId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public ResponseEntity<?> vote(@RequestBody RatedImage ratedImage, final HttpServletRequest request) throws ServletException {
        final Long currentUserId = ClaimGetter.getCurrentUserId(request);
        voteService.add(currentUserId, ratedImage.getId(), ratedImage.getRating());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
