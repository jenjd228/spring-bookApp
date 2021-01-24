package com.example.BookApp.controllers;

import com.example.BookApp.Service.AuthorsService;
import com.example.BookApp.Service.BookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class DefaultController {

    private final Logger logger = Logger.getLogger(DefaultController.class);

    public final BookService bookService;

    public final AuthorsService authorsService;

    DefaultController(BookService bookService,AuthorsService authorsService){
        this.bookService = bookService;
        this.authorsService = authorsService;
    }

    @GetMapping("/index.html")
    public String init (Model model){
        logger.info("/index.html");
        model.addAttribute("bookList",bookService.getBooks());
        return "index";
    }

    @GetMapping("/genres/index.html")
    public String genres(){
        logger.info("/genres/index.html");
        return "genres/index";
    }

    @GetMapping("/authors/index.html")
    public String authors(Model model){
        logger.info("/authors/index.html");
        model.addAttribute("charAndAuthorsList",authorsService.getCharAndAuthorsLists());
        model.addAttribute("charList",authorsService.getOnlyChar());
        return "authors/index";
    }

    @GetMapping("/authors/slug.html")
    public String slug(){
        logger.info("/authors/slug.html");
        return "authors/slug";
    }
}
