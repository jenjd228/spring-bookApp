package com.example.BookApp.Service;

import com.example.BookApp.model.Book2Author;
import com.example.BookApp.model.BookInit;
import com.example.BookApp.model.BookInitDTO;
import com.example.BookApp.repository.AuthorRepository;
import com.example.BookApp.repository.Book2AuthorRepository;
import com.example.BookApp.repository.BooksRepository;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class);

    private final BooksRepository booksRepository;

    private final AuthorRepository authorRepository;

    private final Book2AuthorRepository book2AuthorRepository;

    @Qualifier("modelMapperToBookInitDTO")
    private final ModelMapper modelMapperToBookInitDTO;

    BookService(ModelMapper modelMapperToBookInitDTO, BooksRepository booksRepository, AuthorRepository authorRepository, Book2AuthorRepository book2AuthorRepository) {
        this.modelMapperToBookInitDTO = modelMapperToBookInitDTO;
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
        this.book2AuthorRepository = book2AuthorRepository;
    }

    public ArrayList<BookInitDTO> getAllBooks() {
        logger.info("getBooks");
        List<BookInit> bookInits = booksRepository.findAllBooks();
        List<BookInitDTO> list = bookInits.stream().map(this::convertToDto).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        return new ArrayList<>(list);
    }

    private BookInitDTO convertToDto(BookInit bookInit) {
        if (Objects.isNull(bookInit)) {
            return null;
        }
        BookInitDTO bookInitDTO = modelMapperToBookInitDTO.map(bookInit, BookInitDTO.class);
        bookInitDTO.setPriceNewByDiscount();
        return bookInitDTO;
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
                authorId = getRandomUnmatchedAuthorId(bookId,authorIds,book2Authors);
            }
            book2Authors.add(createBook2Authors(getSortIndex(book2Authors,bookId), bookId, authorId));
        }
        for(int i = 0;i < 20;i++){
            book2Authors.add(getRandomBook2Authors(authorIds,booksIds,book2Authors));
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

    private Book2Author getRandomBook2Authors(List<Integer> authorIds,List<Integer> bookIds,List<Book2Author> book2Authors){
        Book2Author book2Author = new Book2Author();
            Integer bookId = getRandomBookId(bookIds);
            Integer authorId = getRandomUnmatchedAuthorId(bookId,authorIds,book2Authors);
            Integer sortIndex = getSortIndex(book2Authors,bookId);
            book2Author.setBookId(bookId);
            book2Author.setAuthorId(authorId);
            book2Author.setSortIndex(sortIndex);
        return book2Author;
    }

    private Integer getRandomUnmatchedAuthorId(Integer bookId, List<Integer> authorIds, List<Book2Author> book2Authors){
        Integer id = authorIds.get((int) (Math.random() * ((authorIds.size() - 1) + 1)));

        boolean flag = true;

        while (flag){
            int coincidence = 0;
            for (Book2Author book2Author : book2Authors){
                if (book2Author.getBookId() == bookId && book2Author.getAuthorId() == id){
                    id = authorIds.get((int) (Math.random() * ((authorIds.size() - 1) + 1)));
                    coincidence++;
                    break;
                }
            }

            if (coincidence == 0){
                flag = false;
            }
        }

        return id;
    }

    private Integer getRandomBookId(List<Integer> bookIds){
        return bookIds.get((int) (Math.random() * ((bookIds.size() - 1) + 1)));
    }

    private Integer getSortIndex(List<Book2Author> book2Authors,Integer bookId){
        Integer sortIndex = 1;
        for (Book2Author book2Author : book2Authors) {
            if (book2Author.getBookId() == bookId) {
                sortIndex++;
            }
        }
        return sortIndex;
    }
}
