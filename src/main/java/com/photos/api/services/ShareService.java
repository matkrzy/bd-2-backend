package com.photos.api.services;

import com.photos.api.exceptions.EntityGetDeniedException;
import com.photos.api.exceptions.EntityNotFoundException;
import com.photos.api.models.Photo;
import com.photos.api.models.Share;
import com.photos.api.models.User;
import com.photos.api.models.dtos.ShareByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareService {
    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

    public Share getShareFromShareByEmail(ShareByEmail shareByEmail) throws EntityGetDeniedException, EntityNotFoundException {
        Photo photo = photoService.getById((long) shareByEmail.getPhotoId());
        User user = userService.getByEmail(shareByEmail.getUserEmail());

        return new Share(photo, user);
    }
}
