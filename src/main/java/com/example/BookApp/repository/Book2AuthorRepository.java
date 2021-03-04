package com.example.BookApp.repository;

import com.example.BookApp.model.Book2Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2AuthorRepository extends CrudRepository<Book2Author,Integer> {

}
