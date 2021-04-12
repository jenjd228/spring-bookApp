package com.example.BookApp.repository;

import com.example.BookApp.model.BookId2Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookId2RatingRepository extends CrudRepository<BookId2Rating,Integer> {
}
