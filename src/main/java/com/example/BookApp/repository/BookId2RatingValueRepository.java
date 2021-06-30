package com.example.BookApp.repository;

import com.example.BookApp.model.BookIdAndRatingInterface;
import com.example.BookApp.model.BookId2RatingValue;
import com.sun.source.tree.Tree;
import org.modelmapper.internal.Pair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Repository
public interface BookId2RatingValueRepository extends CrudRepository<BookId2RatingValue,Integer> {

    @Query(value = "select n.id as bookId,round((n.sumValue/n.count)) as rating " +
            "from (select b.id,count(b.id) as count,sum(brv.value) as sumValue from book b " +
            "      left join book_id2rating_value brv on b.id = brv.book_id group by b.id) as n",nativeQuery = true)
    List<BookIdAndRatingInterface> getBookIdAndRating();

    @Query(value = "select e from BookId2RatingValue e where e.userId = :userId and e.bookId = :bookId ")
    BookId2RatingValue findBookId2RatingValueByBookIdAndUserId(@Param("userId") Integer userId,@Param("bookId") Integer bookId);
}
