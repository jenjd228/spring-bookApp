package com.example.BookApp.controllers;

import com.example.BookApp.dto.BookDTO;
import com.example.BookApp.dto.BookInitDTO;
import com.example.BookApp.dto.FromToDateDTO;
import com.example.BookApp.dto.SearchWordDto;
import com.example.BookApp.model.Book;
import com.example.BookApp.model.BookReview;
import com.example.BookApp.model.OnlyTagName;
import com.example.BookApp.model.SearchForm;
import com.example.BookApp.repository.BookId2RatingValueRepository;
import com.example.BookApp.repository.BooksRepository;
import com.example.BookApp.repository.TagRepository;
import com.example.BookApp.service.AuthorsService;
import com.example.BookApp.service.BookService;
import com.example.BookApp.service.DefaultService;
import com.example.BookApp.service.UserService;
import liquibase.pro.packaged.B;
import org.apache.log4j.Logger;
import org.dom4j.rule.Mode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class DefaultController {

    private final Logger logger = Logger.getLogger(DefaultController.class);

    public final BookService bookService;

    public final AuthorsService authorsService;

    private final DefaultService defaultService;

    private final BooksRepository booksRepository;

    private final BookId2RatingValueRepository bookId2RatingValueRepository;

    DefaultController(BookId2RatingValueRepository bookId2RatingValueRepository,BooksRepository booksRepository,DefaultService defaultService, BookService bookService, AuthorsService authorsService) {
        this.bookService = bookService;
        this.booksRepository = booksRepository;
        this.authorsService = authorsService;
        this.defaultService = defaultService;
        this.bookId2RatingValueRepository = bookId2RatingValueRepository;
    }

    @PostConstruct()
    public void book2AuthorInit() {
        /*bookService.book2AuthorInit();
        bookService.bookPopularityRefresh();
        bookService.book2GenreInit();
        bookService.book2RatingInit();*/
        //System.out.println(defaultService.getBooksGenreLikeTreeMap().toString());
        //System.out.println(booksRepository.findById(1).get().getComments().get(3).getUser().getId());
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
        return bookService.getRecentBooks(0, 6 ,new FromToDateDTO());
    }

    @ModelAttribute("popularBooks")
    public BookDTO popularBooks() {
        return bookService.getPopularBooks(0, 6);
    }

    @GetMapping(value = {"/", "index"})
    public String mainPage(Model model) {
        logger.info("index");
        //System.out.println(RequestContextHolder.currentRequestAttributes().getSessionId());
        model.addAttribute("tags",defaultService.getAllTags());
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public ResponseEntity getRecommendedBooksPage(@RequestParam(value = "offset" , required = false) Integer offset,
                                                  @RequestParam(value = "limit" , required = false) Integer limit) {
        logger.info("/books/recommended" + offset + " " + limit);
        return new ResponseEntity(bookService.getRecommendedBooks(offset, limit), HttpStatus.OK);
    }

    @GetMapping(value = {"/authors/index", "/authors"})
    public String authors(Model model) {
        logger.info("/authors/index");
        model.addAttribute("charAndAuthorsMap", authorsService.getCharAndAuthorsLists());
        return "authors/index";
    }

    @GetMapping("/authors/slug/{id}")
    public String slug(@PathVariable("id") Integer id, Model model) {
        logger.info("/authors/slug/" + id);
        ResponseEntity r = authorsService.getAuthor(id);
        if (r.getStatusCode().value() == 200) {
            model.addAttribute("author", r.getBody());
        }
        return "authors/slug";
    }

    @GetMapping("/genres/index")
    public String genres(Model model) {
        logger.info("/genres/index");
        model.addAttribute("treeMapOfGenres",defaultService.getBooksGenreLikeTreeMap());
        return "genres/index";
    }

    @GetMapping("/genres/slug")
    public String genresSlug() {
        logger.info("/genres/slug");
        return "genres/slug";
    }

    @GetMapping("/books/author")
    public String booksAuthor(Model model) {
        logger.info("/books/author");
        return "books/author";
    }

    @GetMapping("/books/recent")
    public String booksRecent(@RequestParam(value = "offset" , required = false) Integer offset,
                              @RequestParam(value = "limit" , required = false) Integer limit,
                              @ModelAttribute FromToDateDTO fromToDateDTO,Model model) {
        logger.info("/books/recent " + offset + " " + limit + " " + fromToDateDTO.getFrom());
        return "books/recent";
    }

    @GetMapping("/books/popular")
    public String booksPopular(@RequestParam(value = "offset" , required = false) Integer offset,
                               @RequestParam(value = "limit" , required = false) Integer limit,Model model) {
        logger.info("/books/popular " + offset + " " + limit);
        if (offset != null && limit != null){
            model.addAttribute("popularBooks",bookService.getPopularBooks(offset, limit));
        }else {
            model.addAttribute("popularBooks",bookService.getPopularBooks(0, 20));
        }
        return "/books/popular";
    }

    @GetMapping("/tags/index/{id}")
    public String booksTags(@PathVariable("id") Integer tagId,Model model) {
        logger.info("/tags/index " + tagId);
        model.addAttribute("tagName",new OnlyTagName(defaultService.getTagNameByTagId(tagId)));
        model.addAttribute("booksByTag",bookService.getBookByTagId(0,20,tagId));
        return "/tags/index";
    }

    @GetMapping("/search/{searchWord}")
    public String searchForm(Model model, @PathVariable(value = "searchWord",required = false) SearchWordDto searchWordDto) {
        logger.info("/search/{searchWord} " + searchWordDto);
        model.addAttribute("searchWordDTO", searchWordDto);
        model.addAttribute("searchResults", bookService.getBooksByQuery(searchWordDto.getExample()));
        return "search/index";
    }

    @GetMapping("/books/slug/{id}")
    public String booksSlug(@PathVariable("id") Integer bookId,Model model) {
        logger.info("/books/slug/" + bookId);
        model.addAttribute("bookSlugDTO",bookService.getBookSlugById(bookId));
        return "books/slug";
    }

    @GetMapping("/signin")
    public String signin() {
        logger.info("/signin");
        return "/signin";
    }

    @GetMapping("/signup")
    public String signup() {
        logger.info("/signup");
        return "/signup";
    }

    @GetMapping("/postponed")
    public String postponed(Model model) {
        logger.info("/postponed");
        model.addAttribute("postponedBooksAndIdsDTO",bookService.getPostponedBooks(1));
        return "/postponed";
    }


    /*

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String init(Model model) {
        logger.info("/index");
        model.addAttribute("bookList", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/authors/index")
    public String authors(Model model) {
        logger.info("/authors/index");
        model.addAttribute("charAndAuthorsMap", authorsService.getCharAndAuthorsLists());
        return "authors/index";
    }

    @GetMapping("/cart")
    public String cart() {
        logger.info("/cart");
        return "/cart";
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
