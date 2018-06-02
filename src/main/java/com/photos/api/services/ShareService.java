package com.photos.api.services;

import com.photos.api.models.Share;
import com.photos.api.models.User;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.repositories.PhotoRepository;
import com.photos.api.models.repositories.ShareRepository;
import com.photos.api.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Service
public class ShareService {

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Funkcja dodaje do bazy udostepnienie zdjecia.
     * Sprawdza:
     * - czy udostepnienie nie jest dublowane,
     * - czy udostepniane jest prawidlowe zdjecie,
     * - czy istnieje uzytkownik, ktoremu udostepniane jest zdjecie
     *
     * @param share
     * @return
     */
    public boolean addShare(final Share share) {

        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User owner = userRepository.findByEmail(email);
        try {

            // sprawdzenie czy nie istnieje juz takie udostepnienie w bazie
            if (shareRepository.findByPhotoAndUser(share.getPhoto(), share.getUser()) != null) {
                return false;
            }

            // sprawdzenie czy zdjecie nalezy do zalogowanego uzytkownika
            if (photoRepository.findByPhotoIDAndOwnerAndPhotoState(share.getPhoto().getPhotoID(), owner, PhotoState.ACTIVE) == null) {
                return false;
            }

            // sprawdzenie czy istnieje uzytkownik ktoremu udostepniane jest zdjecie
            if (userRepository.findByUserID(share.getUser().getUserID()) == null) {
                return false;
            }

            share.setOwner(owner);
            shareRepository.save(share);

        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public boolean deleteShare(Share sharee) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User owner = userRepository.findByEmail(email);

        Share share = shareRepository.findByPhotoAndUserAndOwner(sharee.getPhoto(), sharee.getUser(),owner);
        if (share == null) {
            return false;
        }

        try {
            shareRepository.delete(share);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
