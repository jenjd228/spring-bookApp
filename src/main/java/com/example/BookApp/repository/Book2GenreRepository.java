package com.example.BookApp.repository;

import com.example.BookApp.model.Book2Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2GenreRepository extends CrudRepository<Book2Genre, Integer> {

}
