package com.example.BookApp.controllers;

import com.example.BookApp.Service.AuthorsService;
import com.example.BookApp.Service.BookService;
import com.example.BookApp.model.SearchForm;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DefaultController {

    private final Logger logger = Logger.getLogger(DefaultController.class);

    public final BookService bookService;

    public final AuthorsService authorsService;

    DefaultController(BookService bookService, AuthorsService authorsService) {
        this.bookService = bookService;
        this.authorsService = authorsService;
    }

    @ModelAttribute("searchForm")
    public SearchForm recommendedBooks(){
        return new SearchForm();
    }


    @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
    public String init(Model model) {
        logger.info("/index.html");
        model.addAttribute("bookList", bookService.getBooks());
        return "index";
    }

    @GetMapping("/genres/index.html")
    public String genres() {
        logger.info("/genres/index.html");
        return "genres/index";
    }

    @GetMapping("/authors/index.html")
    public String authors(Model model) {
        logger.info("/authors/index.html");
        model.addAttribute("charAndAuthorsMap", authorsService.getCharAndAuthorsLists());
        return "authors/index";
    }

    @GetMapping("/authors/slug.html")
    public String slug() {
        logger.info("/authors/slug.html");
        return "authors/slug";
    }

    @GetMapping("/books/recent.html")
    public String booksRecent() {
        logger.info("books/recent.html");
        return "books/recent";
    }

    @GetMapping("/books/popular.html")
    public String booksPopular() {
        logger.info("/books/popular.html");
        return "/books/popular";
    }

    @GetMapping("/signin.html")
    public String signin() {
        logger.info("/signin.html");
        return "/signin";
    }

    @GetMapping("/cart.html")
    public String cart() {
        logger.info("/cart.html");
        return "/cart";
    }

    @GetMapping("/signup.html")
    public String signup() {
        logger.info("/signup.html");
        return "/signup";
    }

    @GetMapping("/documents/index.html")
    public String documentsIndex() {
        logger.info("/documents/index.html");
        return "/documents/index";
    }

    @GetMapping("/about.html")
    public String about() {
        logger.info("/about.html");
        return "/about";
    }

    @GetMapping("/faq.html")
    public String faq() {
        logger.info("/faq.html");
        return "/faq";
    }

    @GetMapping("/contacts.html")
    public String contacts() {
        logger.info("/contacts.html");
        return "/contacts";
    }

    @GetMapping("/postponed.html")
    public String postponed() {
        logger.info("/postponed.html");
        return "/postponed";
    }

    @GetMapping("/search")
    public String searchForm(Model model) {
        model.addAttribute("searchForm", new SearchForm());
        return "search/index";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute SearchForm searchForm){
        logger.info("/search " + searchForm.getQuery());
        return "search/index";
    }
}
