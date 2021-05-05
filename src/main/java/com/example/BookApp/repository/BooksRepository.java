package com.example.BookApp.repository;

import com.example.BookApp.model.Book;
import com.example.BookApp.model.BookInit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends CrudRepository<Book, Integer> {

    @Query(value = "select book.id as id,slug,title,image,name,price,discount from book left join author on book.id = author.id", nativeQuery = true)
    List<BookInit> findAllBooks();

    @Query("select id from Book")
    List<Integer> findAllBookIds();

    @Query(value = "select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            " (select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            " from book b left " +
            " join author a on b.id = a.id ", nativeQuery = true)
    List<BookInit> findAll(Pageable pageable);

    @Query(value = "select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "order by b.pub_date desc", nativeQuery = true)
    List<BookInit> findRecentBooks(Pageable pageable);

    @Query(value = "select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where b.pub_date < :to " +
            "order by b.pub_date desc", nativeQuery = true)
    List<BookInit> findRecentBooksWhereToIsNotNull(Pageable pageable, @Param("to") String to);

    @Query(value = "select count(*) from (select b.id as id,b.slug,title,image," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where b.pub_date < :to " +
            "order by b.pub_date desc) as count ", nativeQuery = true)
    Integer getCountRecentBooksWhereToIsNotNull(@Param("to") String to);

    @Query(value = "select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where b.pub_date > :from " +
            "order by b.pub_date desc", nativeQuery = true)
    List<BookInit> findRecentBooksWhereFromIsNotNull(Pageable pageable, @Param("from") String from);

    @Query(value = "select count(*) from (select b.id as id,b.slug,title,image," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where b.pub_date > :from " +
            "order by b.pub_date desc) as count", nativeQuery = true)
    Integer getCountRecentBooksWhereFromIsNotNull(@Param("from") String from);

    @Query(value = "select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where a.id = :id " +
            "order by b.pub_date desc", nativeQuery = true)
    List<BookInit> findBooksByAuthorId(Pageable pageable, @Param("id") Integer id);

    @Query(value = "select count(*) from (select b.id as id,b.slug,title,image," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where a.id = :id " +
            "order by b.pub_date desc) as count", nativeQuery = true)
    Integer getCountBooksByAuthorId(@Param("id") Integer id);

    @Query("select count(*) from Book")
    Integer getBookCount();

    @Query(value = "select (select count(*) as PAID from book2user where book_id = ?1 and type_id = 3) " +
            "+ 0.7 * (select count(*) as CARD from book2user where book_id = ?1  AND type_id = 2) " +
            "+ 0.4 * (select count(*) as KEEP from book2user where book_id = ?1  AND type_id = 1) as popularity ", nativeQuery = true)
    Double getBookPopularity(Integer bookId);

    @Query(value = "select b.id AS id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating,(select authors from (select GROUP_CONCAT(a.name) as authors,b.id as bId from book2author ba " +
            "join author a on a.id = ba.author_id " +
            "join book b on b.id = ba.book_id group by book_id) AS t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice from book b " +
            "left join author a on b.id = a.id " +
            "join book_popularity bp on bp.book_id = b.id " +
            "order by bp.popularity desc ", nativeQuery = true)
    List<BookInit> findPopularBooks(Pageable pageable);

    @Query(value = "select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "join book2genre bg on b.id = bg.book_id where bg.genre_id = :genreId " +
            "order by b.pub_date desc ", nativeQuery = true)
    List<BookInit> findBooksByGenreId(Pageable pageable, @Param("genreId") Integer genreId);

    @Query(value = "select count(*) from (select b.id as id,b.slug,title,image, " +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "join book2genre bg on b.id = bg.book_id where bg.genre_id = :genreId " +
            "order by b.pub_date desc) as count ", nativeQuery = true)
    Integer getCountBooksByGenreId(@Param("genreId") Integer genreId);

    @Query(value = "select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "left join tag2book tb on b.id = tb.book_id " +
            "left join tag t on tb.book_id = t.id where  tb.tag_id = :tagId " +
            "order by b.pub_date desc ",nativeQuery = true)
    List<BookInit> findBooksByTagId(Pageable pageable,@Param("tagId") Integer tagId);

    @Query(value = "select count(*) as count from (select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "left join tag2book tb on b.id = tb.book_id " +
            "left join tag t on tb.book_id = t.id where  tb.tag_id = :tagId " +
            "order by b.pub_date desc) as count ",nativeQuery = true)
    Integer getCountBooksByTagId(@Param("tagId") Integer tagId);

    @Query(value = "select b.id as id,b.slug,title,image,(select value from book_id2rating where book_id = b.id)as rating," +
            "(select authors from (select group_concat(a.name) as authors,b.id as bId from book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "left join tag2book tb on b.id = tb.book_id " +
            "left join tag t on tb.book_id = t.id where  b.title like %:query% " +
            "order by b.pub_date desc;" ,nativeQuery = true)
    List<BookInit> getBooksByTitleContaining(@Param("query") String query);
}
