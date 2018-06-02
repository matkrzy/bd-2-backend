package com.photos.api.services;

import com.photos.api.models.Photo;
import com.photos.api.models.Rate;
import com.photos.api.models.User;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.ShareState;
import com.photos.api.models.repositories.PhotoRepository;
import com.photos.api.models.repositories.RateRepository;
import com.photos.api.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Service
public class RateService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RateRepository rateRepository;

    /**
     * Pobiera wszystkie oceny danego zdjecia z bazy oraz wylicza srednia
     *
     * @param
     * @return {srednia ocena zdjecia}
     */
    public int getPhotoRate(final Photo photo) {
        List<Rate> rates = rateRepository.findAllByPhoto(photo);
        return rates.size();
    }

    public boolean addRate(Photo photo) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        Rate check = rateRepository.findByPhotoAndUser(photo, user);
        if (check != null) {
            return false;
        }

        Photo photoo = photoRepository.findByPhotoIDAndPhotoStateAndShareState(photo.getPhotoID(), PhotoState.ACTIVE, ShareState.PUBLIC);
        if (photoo == null) {
            return false;
        }

        try {
            Rate rate = new Rate();
            rate.setPhoto(photo);
            rate.setUser(user);
            rate.setDate(new Timestamp(System.currentTimeMillis()));
            rateRepository.save(rate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteRate(Photo photo) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        Rate rate = rateRepository.findByPhotoAndUser(photo, user);
        if (rate == null) {
            return false;
        }
        try {
            rateRepository.delete(rate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public List<Rate> getAll() {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        return rateRepository.findAllByUser(user);
    }
}

