package com.photos.api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author Micha Królewski on 2018-04-08.
 * @version 1.0
 */

@ApiModel
public class ShareByEmail {
    @NotNull
    @JsonProperty("photoId")
    @ApiModelProperty(required = true, dataType = "int")
    private int photoId;

    @NotNull
    @JsonProperty("userEmail")
    @ApiModelProperty(required = true, dataType = "String")
    private String userEmail;

    public ShareByEmail() {
    }

    public ShareByEmail(
            @NotNull int photoId,
            @NotNull String userEmail
    ) {
        this.photoId = photoId;
        this.userEmail = userEmail;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
