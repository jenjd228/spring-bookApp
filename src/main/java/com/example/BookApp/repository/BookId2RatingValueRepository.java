package com.example.BookApp.repository;

import com.example.BookApp.model.BookIdAndRatingInterface;
import com.example.BookApp.model.BookId2RatingValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookId2RatingValueRepository extends CrudRepository<BookId2RatingValue,Integer> {

    @Query(value = "select n.id as bookId,round((n.sumValue/n.count)) as rating " +
            "from (select b.id,count(b.id) as count,sum(brv.value) as sumValue from book b " +
            "      left join book_id2rating_value brv on b.id = brv.book_id group by b.id) as n",nativeQuery = true)
    List<BookIdAndRatingInterface> getBookIdAndRating();
}
