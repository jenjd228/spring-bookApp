package com.example.BookApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/index.html")
    public String init (){
        return "index";
    }

    @GetMapping("/genres/index.html")
    public String genres(){
        return "genres/index";
    }

    @GetMapping("/authors/index.html")
    public String authors(){
        return "authors/index";
    }
}
