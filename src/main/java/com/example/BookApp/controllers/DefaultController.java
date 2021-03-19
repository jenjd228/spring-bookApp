package com.example.BookApp.controllers;

import com.example.BookApp.dto.BookDTO;
import com.example.BookApp.dto.SearchWordDto;
import com.example.BookApp.service.AuthorsService;
import com.example.BookApp.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Controller
public class DefaultController {

    private final Logger logger = Logger.getLogger(DefaultController.class);

    public final BookService bookService;

    public final AuthorsService authorsService;

    DefaultController(BookService bookService, AuthorsService authorsService) {
        this.bookService = bookService;
        this.authorsService = authorsService;
    }

    @PostConstruct()
    public void book2AuthorInit(){
        //bookService.book2AuthorInit();
        //bookService.bookPopularityRefresh();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("recommendedBooks")
    public BookDTO recommendedBooks() {
        return bookService.getRecommendedBooks(0, 6);
    }

    @ModelAttribute("recentBooks")
    public BookDTO recentBooks() {
        return bookService.getNewBooks(0,6);
    }

    @ModelAttribute("popularBooks")
    public BookDTO popularBooks() {
        return bookService.getPopularBooks(0,6);
    }

    @GetMapping("/")
    public String mainPage() {
        logger.info("index");
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public ResponseEntity getRecommendedBooksPage(@RequestParam("offset") Integer offset,
                                                  @RequestParam("limit") Integer limit) {
        logger.info("/books/recommended");
        return new ResponseEntity(bookService.getRecommendedBooks(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public ResponseEntity getRecentBooksPage(@RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        logger.info("/books/recent");
        return new ResponseEntity(bookService.getNewBooks(offset, limit),HttpStatus.OK);
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public ResponseEntity getPopularBooksPage(@RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        logger.info("/books/popular");
        return new ResponseEntity(bookService.getPopularBooks(offset, limit),HttpStatus.OK);
    }
    /*@ModelAttribute("searchForm")
    public SearchForm recommendedBooks(){
        return new SearchForm();
    }


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String init(Model model) {
        logger.info("/index");
        model.addAttribute("bookList", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/genres/index")
    public String genres() {
        logger.info("/genres/index");
        return "genres/index";
    }

    @GetMapping("/authors/index")
    public String authors(Model model) {
        logger.info("/authors/index");
        model.addAttribute("charAndAuthorsMap", authorsService.getCharAndAuthorsLists());
        return "authors/index";
    }

    @GetMapping("/authors/slug")
    public String slug() {
        logger.info("/authors/slug");
        return "authors/slug";
    }

    @GetMapping("/books/recent")
    public String booksRecent(Model model) {
        logger.info("/books/recent");
        model.addAttribute("fromToDateDTO",new FromToDateDTO());
        return "books/recent";
    }

    @GetMapping("/books/popular")
    public String booksPopular() {
        logger.info("/books/popular");
        return "/books/popular";
    }

    @GetMapping("/signin")
    public String signin() {
        logger.info("/signin");
        return "/signin";
    }

    @GetMapping("/cart")
    public String cart() {
        logger.info("/cart");
        return "/cart";
    }

    @GetMapping("/signup")
    public String signup() {
        logger.info("/signup");
        return "/signup";
    }

    @GetMapping("/documents/index")
    public String documentsIndex() {
        logger.info("/documents/index");
        return "/documents/index";
    }

    @GetMapping("/about")
    public String about() {
        logger.info("/about");
        return "/about";
    }

    @GetMapping("/faq")
    public String faq() {
        logger.info("/faq");
        return "/faq";
    }

    @GetMapping("/contacts")
    public String contacts() {
        logger.info("/contacts");
        return "/contacts";
    }

    @GetMapping("/postponed")
    public String postponed() {
        logger.info("/postponed");
        return "/postponed";
    }

    @GetMapping("/search/{query}")
    public String searchForm(Model model, @PathVariable String query) {
        System.out.println(query);
        model.addAttribute("searchForm", new SearchForm());
        return "search/index";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute SearchForm searchForm){
        logger.info("/search " + searchForm.getQuery());
        return "search/index";
    }
//'https://virtserver.swaggerhub.com/lunpully/bookshop/1.0.0'+address
    @GetMapping ("/booksByTimeInterval")
    public void booksRecentForm(@ModelAttribute FromToDateDTO fromToDateDTO){
        System.out.println(fromToDateDTO.toString());
    }*/
}
