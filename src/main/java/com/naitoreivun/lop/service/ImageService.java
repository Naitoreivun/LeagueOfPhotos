package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.ImageDAO;
import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.VotableImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageDAO imageDAO;

    @Autowired
    private ContestService contestService;

    @Autowired
    private UserService userService;

    @Autowired
    private VoteService voteService;


    public boolean add(MultipartFile file, String title, Long contestId, Long userId) {
        Contest contest = contestService.getContestById(contestId);
        User user = userService.getById(userId);
        deleteByContestAndUserIfExists(contest, user);
        try {
            Image img = new Image(title, file.getBytes(), contest, user);

            imageDAO.save(img);
//            String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            String newFilename = title + originalFileExtension;
//            String storageDirectory = System.getProperty("user.home") + "/Desktop/foty";
//            File newFile = new File(storageDirectory + "/" + newFilename);
//            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: 2016-06-02  ???
            return false;
        }
        return true;
    }

    private void deleteByContestAndUserIfExists(Contest contest, User user) {
        Optional<Image> image = imageDAO.findByContestAndUser(contest, user);
        if (image.isPresent()) {
            imageDAO.delete(image.get());
        }
    }

    public Image getByContestIdAndUserId(Long contestId, Long userId) {
        Contest contest = contestService.getContestById(contestId);
        User user = userService.getById(userId);
        return imageDAO.findByContestAndUser(contest, user).get();
    }

    public List<VotableImage> getVotableImagesByContestId(Long contestId, Long currentUserId) {
        Contest contest = contestService.getContestById(contestId);
        User user = userService.getById(currentUserId);

        return imageDAO.findByContestAndUserNot(contest, user)
                .stream()
                .sorted(Comparator.comparing(Image::getCreationDate))
                .map(image -> new VotableImage(image, voteService.getRatingByUserAndImage(user, image)))
                .collect(Collectors.toList());
    }

    public Image getById(Long id) {
        return imageDAO.findById(id).get();
    }

    public List<Image> getByContestId(Long contestId) {
        Contest contest = contestService.getContestById(contestId);
        return contest.getImages();
    }
}
