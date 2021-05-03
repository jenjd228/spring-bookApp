package com.example.BookApp.repository;

import com.example.BookApp.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag,Integer> {

    @Query("select name from Tag t where t.id = :tagId ")
    String findTagByTagId(@Param("tagId") Integer tagId);
}
