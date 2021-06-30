package com.example.BookApp.controllers;

import com.example.BookApp.service.BookService;
import net.minidev.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    private final BookService bookService;

    @Autowired
    PostController(BookService bookService){
        this.bookService = bookService;
    }

    private final Logger logger = Logger.getLogger(PostController.class);

    @PostMapping("/changeBookStatus")
    public JSONObject changeBookStatus(@RequestParam("booksIds[]") List<Integer> booksIds, @RequestParam("status") String status) {
        logger.info("/changeBookStatus " + booksIds + " " + status);
        return bookService.changeBookStatus(booksIds,status,6);
    }

    /*@PostMapping("/changeBookStatus")
    public void changeBookStatuss(@RequestParam("bookId") List<Integer> bookId, @RequestParam("value") Integer value) {
        logger.info("/changeBookStatus " + value);
        //return bookService.changeBookStatus(booksIds,status,6);
    }*/
}
