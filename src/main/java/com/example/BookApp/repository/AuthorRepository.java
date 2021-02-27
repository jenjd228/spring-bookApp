package com.example.BookApp.repository;

import com.example.BookApp.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Integer> {

    Optional<Author> findById(Integer integer);
}
