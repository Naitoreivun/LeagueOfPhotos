package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.VoteDAO;
import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteDAO voteDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;


    public void add(Long voterId, Long imageId, Short rating) {
        final User voter = userService.getById(voterId);
        final Image image = imageService.getById(imageId);
        final Vote vote = new Vote(voter, image, rating);
        voteDAO.save(vote);
    }

    public Short getRatingByUserAndImage(User user, Image image) {
        final Optional<Vote> vote = voteDAO.findByUserAndImage(user, image);
        return vote.isPresent() ? vote.get().getRating() : (short) 0;
    }

    public Short getTotalRatingByImage(Image image) {
        return voteDAO.getTotalRatingByImage(image);
    }
}
