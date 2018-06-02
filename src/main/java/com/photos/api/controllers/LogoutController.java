package com.photos.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Micha Królewski on 2018-05-27.
 * @version x
 */

@RestController
public class LogoutController {

    @PostMapping("/account/logout")
    public void logout() {
    }
}
