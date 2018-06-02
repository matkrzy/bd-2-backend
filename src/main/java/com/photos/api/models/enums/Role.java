package com.photos.api.models.enums;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */


public enum Role {


    USER("USER"),
    ADMIN("ADMIN");

    /**
     *
     */
    private String text;

    /**
     * Constructor
     * @param text
     */
    Role(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return this.text;
    }
}
