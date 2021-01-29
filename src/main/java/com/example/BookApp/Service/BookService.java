package com.example.BookApp.Service;

import com.example.BookApp.model.Book;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class);

    private List<Book> books;

    private final JdbcTemplate jdbcTemplate;

    BookService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        books = new ArrayList<>();
    }

    public ArrayList<Book> getBooks(){
        if (books.size() == 0){
            initBooksArray();
        }
        logger.info("getBooks "+ Arrays.toString(books.toArray()));
        return new ArrayList<>(books);
    }

    private void initBooksArray(){
        logger.info("books init");
        books = jdbcTemplate.query("select * from books left join authors on books.author_id = authors.id",(ResultSet resultSet, int rowNum)->{
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("fio"));
            book.setPriceOld(resultSet.getString("priceOld"));
            book.setPrice(resultSet.getString("price"));
            return book;
        });
    }
}
