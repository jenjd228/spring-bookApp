package com.example.BookApp.repository;

import com.example.BookApp.model.Book2User;
import com.example.BookApp.model.PostponedBooksInterface;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book2UserRepository extends CrudRepository<Book2User,Integer> {

    @Query(value = "select book_id as bookId,image,title,price," +
            "(select name from book2author ba join author a on ba.author_id = a.id where sort_index = 1 and book_id = b.id) as authorName," +
            "(select author_id from book2author ba join author a on ba.author_id = a.id where sort_index = 1 and book_id = b.id) as authorId," +
            "(select value from book_id2rating where book_id = b.id)as rating," +
            "round(price - (price * discount/100)) as discountPrice from book2user bu " +
            "join book b on b.id = bu.book_id " +
            "where bu.user_id = 6 and bu.type_id = 1 ",nativeQuery = true)
    List<PostponedBooksInterface> findPostponedBooks(@Param("userId") Integer userId);

    @Query(value = "select * from book2user where user_id = :userId and book_id = :bookId ",nativeQuery = true)
    Book2User findBookByBookIdAndUserId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
