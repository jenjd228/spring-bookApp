package com.example.BookApp.repository;

import com.example.BookApp.model.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre,Integer> {
    @Query(value = "select id from Genre")
    List<Integer> findAllGenreIds();
}
