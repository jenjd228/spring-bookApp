package com.example.BookApp.repository;

import com.example.BookApp.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

    @Query(value = "select id from Author")
    List<Integer> findAllAuthorIds();

    Author findAuthorById(Integer integer);

    @Override
    List<Author> findAll();
}
