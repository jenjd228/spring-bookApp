package com.example.BookApp.api;

import com.example.BookApp.dto.FromToDateDTO;
import com.example.BookApp.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ApiDefaultController {

    private final Logger logger = Logger.getLogger(ApiDefaultController.class);

    private final BookService bookService;

    @Autowired
    ApiDefaultController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/recommended")
    public ResponseEntity getRecommendedBooks(@RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit) {
        logger.info("api/books/recommended " + offset + " " + limit);
        return new ResponseEntity(bookService.getRecommendedBooks(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/books/recent")
    public ResponseEntity getRecentBooks(@RequestParam("offset") Integer offset,
                                         @RequestParam("limit") Integer limit,
                                         FromToDateDTO fromToDateDTO) {
        logger.info("api/books/recent " + offset + " " + limit + " " + fromToDateDTO);
        return new ResponseEntity(bookService.getRecentBooks(offset, limit, fromToDateDTO), HttpStatus.OK);
    }

    @GetMapping("/books/popular")
    public ResponseEntity getPopularBooks(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit) {
        logger.info("api/books/popular" + offset);
        return new ResponseEntity(bookService.getPopularBooks(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/books/authors/{id}")
    public ResponseEntity getBooksByAuthorId(@RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit,
                                             @PathVariable("id") Integer authorId) {
        logger.info("api/books/authors/{id} " + offset + " " + limit + " " + authorId);
        return new ResponseEntity(bookService.getBooksByAuthorId(offset, limit, authorId), HttpStatus.OK);
    }

    @GetMapping("/books/genre/{id}")
    public ResponseEntity getBooksByGenreId(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit,
                                            @PathVariable("id") Integer genreId) {
        logger.info("/api/books/genre/{id} " + offset + " " + limit + " " + genreId);
        return new ResponseEntity(bookService.getBooksByGenreId(offset, limit, genreId), HttpStatus.OK);
    }

}
