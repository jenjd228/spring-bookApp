package com.example.BookApp.service;

import com.example.BookApp.dto.*;
import com.example.BookApp.model.*;
import com.example.BookApp.repository.*;
import liquibase.pro.packaged.B;
import net.minidev.json.JSONObject;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class);

    private final BooksRepository booksRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final Book2AuthorRepository book2AuthorRepository;

    private final Book2GenreRepository book2GenreRepository;

    private final BookId2RatingRepository bookId2RatingRepository;

    private final Book2UserRepository book2UserRepository;

    private final BookId2RatingValueRepository bookId2RatingValueRepository;

    private final BookPopularityRepository bookPopularityRepository;

    private final UserRepository userRepository;

    @Qualifier("modelMapperToBookInitDTO")
    private final ModelMapper modelMapperToBookInitDTO;

    @Qualifier("modelMapperToBookIdAndRating")
    private final ModelMapper modelMapperToBookIdAndRating;
    
    @Qualifier("modelMapperToBookSlugDTO")
    private final ModelMapper modelMapperToBookSlugDTO;

    @Qualifier("modelMapperToPostponedBooksDTO")
    private final ModelMapper modelMapperToPostponedBooksDTO;

    BookService(UserRepository userRepository,ModelMapper modelMapperToPostponedBooksDTO,Book2UserRepository book2UserRepository,ModelMapper modelMapperToBookSlugDTO,BookId2RatingRepository bookId2RatingRepository,ModelMapper modelMapperToBookIdAndRating, BookId2RatingValueRepository bookId2RatingValueRepository, Book2GenreRepository book2GenreRepository, GenreRepository genreRepository, BookPopularityRepository bookPopularityRepository, ModelMapper modelMapperToBookInitDTO, BooksRepository booksRepository, AuthorRepository authorRepository, Book2AuthorRepository book2AuthorRepository) {
        this.book2GenreRepository = book2GenreRepository;
        this.genreRepository = genreRepository;
        this.bookPopularityRepository = bookPopularityRepository;
        this.modelMapperToBookInitDTO = modelMapperToBookInitDTO;
        this.modelMapperToBookIdAndRating = modelMapperToBookIdAndRating;
        this.modelMapperToBookSlugDTO = modelMapperToBookSlugDTO;
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
        this.book2AuthorRepository = book2AuthorRepository;
        this.bookId2RatingValueRepository = bookId2RatingValueRepository;
        this.bookId2RatingRepository = bookId2RatingRepository;
        this.book2UserRepository = book2UserRepository;
        this.modelMapperToPostponedBooksDTO = modelMapperToPostponedBooksDTO;
        this.userRepository = userRepository;
    }

    public void book2GenreInit() {
        List<Integer> booksIds = booksRepository.findAllBookIds();
        List<Integer> genresIds = genreRepository.findAllGenreIds();
        List<Book2Genre> book2Genres = new ArrayList<>();

        int currentGenreIndex = 0;
        int maxGenresIndex = genresIds.size();

        for (Integer booksId : booksIds) {
            Book2Genre book2Genre = new Book2Genre();
            Book2Genre.BookId2genreKey bookId2genreKey = new Book2Genre.BookId2genreKey();
            bookId2genreKey.setBookId(booksId);
            bookId2genreKey.setGenreId(genresIds.get(currentGenreIndex));
            book2Genre.setKey(bookId2genreKey);
            book2Genres.add(book2Genre);
            currentGenreIndex++;
            if (currentGenreIndex == maxGenresIndex) {
                currentGenreIndex = 0;
            }
        }

        for (int i = 0; i < 22; i++) {
            Book2Genre book2Genre = new Book2Genre();
            Book2Genre.BookId2genreKey bookId2genreKey = new Book2Genre.BookId2genreKey();
            Integer bookId = getRandomBookId(booksIds);
            bookId2genreKey.setBookId(bookId);
            bookId2genreKey.setGenreId(getRandomUnmatchedGenreId(bookId, genresIds, book2Genres));
            book2Genre.setKey(bookId2genreKey);
            book2Genres.add(book2Genre);
        }

        book2GenreRepository.saveAll(book2Genres);
    }

    public void book2RatingInit() {
        List<BookIdAndRatingInterface> bookIdAndRatings = bookId2RatingValueRepository.getBookIdAndRating();
        List<BookIdAndRating> list = bookIdAndRatings.stream().map(this::convertToBookIdAndRating).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        for (BookIdAndRating bookIdAndRating : list){
            BookId2Rating bookId2Rating = new BookId2Rating();
            bookId2Rating.setBookId(bookIdAndRating.getBookId());
            if (bookIdAndRating.getRating() == null){
                bookId2Rating.setValue((byte) 0);
            }else {
                bookId2Rating.setValue(bookIdAndRating.getRating());
            }
            bookId2RatingRepository.save(bookId2Rating);
        }
    }

    private Integer getRandomUnmatchedGenreId(Integer bookId, List<Integer> genresIds, List<Book2Genre> book2Genres) {
        Integer genreId = genresIds.get((int) (Math.random() * ((genresIds.size() - 1) + 1)));

        boolean flag = true;

        while (flag) {
            int coincidence = 0;
            for (Book2Genre book2Genre : book2Genres) {
                if (bookId == book2Genre.getKey().getBookId() && book2Genre.getKey().getGenreId() == genreId) {
                    genreId = genresIds.get((int) (Math.random() * ((genresIds.size() - 1) + 1)));
                    coincidence++;
                    break;
                }
            }

            if (coincidence == 0) {
                flag = false;
            }
        }

        return genreId;
    }

    public void book2AuthorInit() {
        List<Book2Author> book2Authors = new ArrayList<>();

        List<Integer> booksIds = booksRepository.findAllBookIds();
        List<Integer> authorIds = authorRepository.findAllAuthorIds();

        for (Integer bookId : booksIds) {
            Integer authorId;
            if (authorIds.contains(bookId)) {
                authorId = bookId;
            } else {
                authorId = getRandomUnmatchedAuthorId(bookId, authorIds, book2Authors);
            }
            book2Authors.add(createBook2Authors(getSortIndex(book2Authors, bookId), bookId, authorId));
        }
        for (int i = 0; i < 20; i++) {
            book2Authors.add(getRandomBook2Authors(authorIds, booksIds, book2Authors));
        }
        book2AuthorRepository.saveAll(book2Authors);
    }

    private Book2Author createBook2Authors(Integer sortIndex, Integer bookId, Integer authorId) {
        Book2Author book2Author = new Book2Author();
        book2Author.setBookId(bookId);
        book2Author.setAuthorId(authorId);
        book2Author.setSortIndex(sortIndex);
        return book2Author;
    }

    private Book2Author getRandomBook2Authors(List<Integer> authorIds, List<Integer> bookIds, List<Book2Author> book2Authors) {
        Book2Author book2Author = new Book2Author();
        Integer bookId = getRandomBookId(bookIds);
        Integer authorId = getRandomUnmatchedAuthorId(bookId, authorIds, book2Authors);
        Integer sortIndex = getSortIndex(book2Authors, bookId);
        book2Author.setBookId(bookId);
        book2Author.setAuthorId(authorId);
        book2Author.setSortIndex(sortIndex);
        return book2Author;
    }

    private Integer getRandomUnmatchedAuthorId(Integer bookId, List<Integer> authorIds, List<Book2Author> book2Authors) {
        Integer id = authorIds.get((int) (Math.random() * ((authorIds.size() - 1) + 1)));

        boolean flag = true;

        while (flag) {
            int coincidence = 0;
            for (Book2Author book2Author : book2Authors) {
                if (book2Author.getBookId() == bookId && book2Author.getAuthorId() == id) {
                    id = authorIds.get((int) (Math.random() * ((authorIds.size() - 1) + 1)));
                    coincidence++;
                    break;
                }
            }

            if (coincidence == 0) {
                flag = false;
            }
        }

        return id;
    }

    private Integer getRandomBookId(List<Integer> bookIds) {
        return bookIds.get((int) (Math.random() * ((bookIds.size() - 1) + 1)));
    }

    private Integer getSortIndex(List<Book2Author> book2Authors, Integer bookId) {
        Integer sortIndex = 1;
        for (Book2Author book2Author : book2Authors) {
            if (book2Author.getBookId() == bookId) {
                sortIndex++;
            }
        }
        return sortIndex;
    }

    public void bookPopularityRefresh() {
        bookPopularityRepository.deleteAll();

        List<BookPopularity> list = new ArrayList<>();
        Integer bookCount = booksRepository.getBookCount();
        for (int i = 1; i <= bookCount; i++) {
            list.add(createBookPopularity(i));
        }
        bookPopularityRepository.saveAll(list);
    }

    private BookPopularity createBookPopularity(Integer bookId) {
        Double popularity = booksRepository.getBookPopularity(bookId);
        BookPopularity bookPopularity = new BookPopularity();
        BookPopularity.BookId2popularityKey bookId2popularityKey = new BookPopularity.BookId2popularityKey();
        bookId2popularityKey.setBookId(bookId);
        bookId2popularityKey.setPopularity(popularity);
        bookPopularity.setKey(bookId2popularityKey);
        return bookPopularity;
    }

    public ArrayList<BookInitDTO> getAllBooks() {
        logger.info("getBooks");
        List<BookInit> booksInit = booksRepository.findAllBooks();
        List<BookInitDTO> list = booksInit.stream().map(this::convertToBookInitDTO).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new ArrayList<>(list);
    }

    public BookDTO getRecommendedBooks(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<BookInit> booksInit = booksRepository.findAll(pageable);
        List<BookInitDTO> list = booksInit.stream().map(this::convertToBookInitDTO).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new BookDTO(booksRepository.getBookCount(), list);
    }

    public BookDTO getRecentBooks(Integer offset, Integer limit, FromToDateDTO fromToDateDTO) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<BookInit> booksInit;
        Integer bookCount;

        if (fromToDateDTO.getFrom() == null && fromToDateDTO.getTo() == null) {
            booksInit = booksRepository.findRecentBooks(pageable);
            bookCount = booksRepository.getBookCount();
        } else if (fromToDateDTO.getFrom() == null) {
            booksInit = booksRepository.findRecentBooksWhereToIsNotNull(pageable, fromToDateDTO.getTo());
            bookCount = booksRepository.getCountRecentBooksWhereToIsNotNull(fromToDateDTO.getTo());
        } else {
            booksInit = booksRepository.findRecentBooksWhereFromIsNotNull(pageable, fromToDateDTO.getFrom());
            bookCount = booksRepository.getCountRecentBooksWhereFromIsNotNull(fromToDateDTO.getFrom());
        }

        List<BookInitDTO> list = booksInit.stream().map(this::convertToBookInitDTO).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new BookDTO(bookCount, list);
    }

    public BookDTO getPopularBooks(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<BookInit> booksInit = booksRepository.findPopularBooks(pageable);
        List<BookInitDTO> list = booksInit.stream().map(this::convertToBookInitDTO).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new BookDTO(booksRepository.getBookCount(), list);
    }

    public BookDTO getBooksByAuthorId(Integer offset, Integer limit, Integer id) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<BookInit> booksInit = booksRepository.findBooksByAuthorId(pageable, id);
        List<BookInitDTO> list = booksInit.stream().map(this::convertToBookInitDTO).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new BookDTO(booksRepository.getCountBooksByAuthorId(id), list);
    }

    public BookDTO getBooksByGenreId(Integer offset, Integer limit, Integer genreId) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<BookInit> booksInit = booksRepository.findBooksByGenreId(pageable, genreId);
        List<BookInitDTO> list = booksInit.stream().map(this::convertToBookInitDTO).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new BookDTO(booksRepository.getCountBooksByGenreId(genreId), list);
    }

    public BookDTO getBookByTagId(Integer offset, Integer limit,Integer tagId){
        Pageable pageable = PageRequest.of(offset, limit);
        List<BookInit> booksInit = booksRepository.findBooksByTagId(pageable, tagId);
        List<BookInitDTO> list = booksInit.stream().map(this::convertToBookInitDTO).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new BookDTO(booksRepository.getCountBooksByTagId(tagId), list);
    }

    public BookDTO getBooksByQuery(String query) {
        List<BookInit> booksInit = booksRepository.findBooksByTitleContaining(query);
        List<BookInitDTO> list = booksInit.stream().map(this::convertToBookInitDTO).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new BookDTO(list.size(), list);
    }

    public BookSlugDTO getBookSlugById(Integer id){
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()){
            BookSlugDTO bookSlugDTO = modelMapperToBookSlugDTO.map(book.get(),BookSlugDTO.class);
            for (CommentDTO commentDTO : bookSlugDTO.getComments()){
                BookId2RatingValue bookId2RatingValue = bookId2RatingValueRepository.findBookId2RatingValueByBookIdAndUserId(
                        commentDTO.getUser().getId(),commentDTO.getId());
                int rating = 0;
                if (bookId2RatingValue != null){
                    rating = bookId2RatingValue.getValue();
                }
                commentDTO.setUserRating(rating);
            }
            bookSlugDTO.setCommentsCount(bookSlugDTO.getComments().size());
            System.out.println(bookSlugDTO);
            return bookSlugDTO;
        }
        return new BookSlugDTO();
    }

    private BookSlugDTO convertToBookSlugDTO(BookSlug bookSlug){
        return bookSlug == null ? null : modelMapperToBookSlugDTO.map(bookSlug,BookSlugDTO.class);
    }

    private BookInitDTO convertToBookInitDTO(BookInit bookInit) {
        return bookInit == null ? null : modelMapperToBookInitDTO.map(bookInit, BookInitDTO.class);
    }

    private BookIdAndRating convertToBookIdAndRating(BookIdAndRatingInterface bookIdAndRatingInterface) {
        return bookIdAndRatingInterface == null ? null : modelMapperToBookIdAndRating.map(bookIdAndRatingInterface, BookIdAndRating.class);
    }

    private PostponedBooksDTO convertToPostponedBooksDTO(PostponedBooksInterface postponedBooksInterface) {
        return postponedBooksInterface == null ? null : modelMapperToBookIdAndRating.map(postponedBooksInterface, PostponedBooksDTO.class);
    }

    public PostponedBooksAndIdsDTO getPostponedBooks(int userId) {
        List<PostponedBooksInterface> list = book2UserRepository.findPostponedBooks(userId);
        List<PostponedBooksDTO> list2 = list.stream().map(this::convertToPostponedBooksDTO)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        List<Integer> listIds = list2.stream().map(PostponedBooksDTO::getBookId).collect(Collectors.toList());
        return new PostponedBooksAndIdsDTO(list2,listIds);
    }

    public JSONObject changeBookStatus(List<Integer> booksIds, String status,Integer userId) {
        JSONObject json = new JSONObject();
        boolean result = true;
        int typeId = getBookType(status);

        for(Integer bookId : booksIds){
            Book2User book2User = book2UserRepository.findBookByBookIdAndUserId(userId,bookId);
            if (book2User != null){
                if (status.equals("UNLINK")){
                    book2UserRepository.delete(book2User);
                }else {
                    if (typeId != book2User.getTypeId()){
                        if (book2User.getTypeId() == 2 && typeId == 1){
                            result = false;
                            json.put("error","Нельзя отложить купленную книгу");
                        }else {
                            book2User.setTypeId(typeId);
                            book2UserRepository.save(book2User);
                        }
                    }
                }
            }else {
                Optional<User> user = userRepository.findById(userId);
                if (user.isPresent()){
                    book2UserRepository.save(getNewBook2User(typeId,userId,bookId));
                    result = true;
                }else {
                    result = false;
                    break;
                }
            }
        }
        json.put("result",result);
        return json;
    }

    private Integer getBookType(String status){
        switch (status){
            case "CART" -> {
                return 2;
            }
            case "KEPT" -> {
                return 1;
            }
            case "ARCHIVED" -> {
                return 4;
            }
        }
        return 0;
    }

    private Book2User getNewBook2User(Integer typeId,Integer userId, Integer bookId) {
        Book2User book2User = new Book2User();
        book2User.setBookId(bookId);
        book2User.setUserId(userId);
        book2User.setTypeId(typeId);
        book2User.setTime(LocalDateTime.now());
        return book2User;
    }
}
