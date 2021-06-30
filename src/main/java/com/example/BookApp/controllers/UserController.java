package com.example.BookApp.controllers;

import com.example.BookApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/requestContactConfirmation")
    public void requestContactConfirmation(@RequestParam("contact") String contact){
        userService.sendCode(contact);
    }
}
