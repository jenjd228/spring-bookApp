package com.example.BookApp.repository;

import com.example.BookApp.model.BookPopularity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPopularityRepository extends CrudRepository<BookPopularity,Integer> {

}
