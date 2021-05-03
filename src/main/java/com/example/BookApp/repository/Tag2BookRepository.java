package com.example.BookApp.repository;

import com.example.BookApp.model.Tag2Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tag2BookRepository extends CrudRepository<Tag2Book,Integer> {
}
