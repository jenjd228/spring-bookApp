package com.example.BookApp.repository;

import com.example.BookApp.model.Book;
import com.example.BookApp.model.BookInit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends CrudRepository<Book,Integer> {

    @Query(value = "select book.id as bookId,title,name,price,discount from book left join author on book.id = author.id",nativeQuery = true)
    List<BookInit> findAllBooks();

    @Query("select id from Book")
    List<Integer> findAllBookIds();

}
