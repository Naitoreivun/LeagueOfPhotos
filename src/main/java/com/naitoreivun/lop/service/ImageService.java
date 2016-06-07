package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.ImageDAO;
import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageDAO imageDAO;

    @Autowired
    private ContestService contestService;

    @Autowired
    private UserService userService;


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
}
