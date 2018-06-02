package com.photos.api.models;

import java.util.List;

/**
 * @author Micha Królewski on 2018-05-13.
 * @version x
 */


public class ResponsePhoto extends Photo {

    private int rate;
    private List<Tag> tags;

    public ResponsePhoto(Photo photo, int rate, List<Tag> tags) {
        super(photo.getName(), photo.getOwner(), photo.getPath(), photo.getUploadTime(), photo.getDescription(), photo.getShareState(), photo.getPhotoState());
        this.setPhotoID(photo.getPhotoID());
        this.rate = rate;
        this.tags = tags;
    }

    public ResponsePhoto() {

    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
