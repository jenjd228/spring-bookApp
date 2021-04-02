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

    @Query(value = "select b.id as id,b.slug,title,image," +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left " +
            "join author a on b.id = a.id", nativeQuery = true)
    List<BookInit> findAll(Pageable pageable);

    @Query(value = "select b.id as id,b.slug,title,image," +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "ORDER BY b.pub_date DESC", nativeQuery = true)
    List<BookInit> findRecentBooks(Pageable pageable);

    @Query(value = "select b.id as id,b.slug,title,image," +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where b.pub_date < :to " +
            "ORDER BY b.pub_date DESC", nativeQuery = true)
    List<BookInit> findRecentBooksWhereToIsNotNull(Pageable pageable, @Param("to") String to);

    @Query(value = "SELECT COUNT(*) FROM (select b.id as id,b.slug,title,image," +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where b.pub_date < :to " +
            "ORDER BY b.pub_date DESC) as count ", nativeQuery = true)
    Integer getCountRecentBooksWhereToIsNotNull(@Param("to") String to);

    @Query(value = "select b.id as id,b.slug,title,image," +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where b.pub_date > :from " +
            "ORDER BY b.pub_date DESC", nativeQuery = true)
    List<BookInit> findRecentBooksWhereFromIsNotNull(Pageable pageable, @Param("from") String from);

    @Query(value = "SELECT COUNT(*) FROM (select b.id as id,b.slug,title,image," +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where b.pub_date > :from " +
            "ORDER BY b.pub_date DESC) AS count", nativeQuery = true)
    Integer getCountRecentBooksWhereFromIsNotNull(@Param("from") String from);

    @Query(value = "select b.id as id,b.slug,title,image," +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where a.id = :id " +
            "ORDER BY b.pub_date DESC", nativeQuery = true)
    List<BookInit> findBooksByAuthorId(Pageable pageable, @Param("id") Integer id);

    @Query(value = "SELECT COUNT(*) FROM (select b.id as id,b.slug,title,image," +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id where a.id = :id " +
            "ORDER BY b.pub_date DESC) AS count", nativeQuery = true)
    Integer getCountBooksByAuthorId(@Param("id") Integer id);

    @Query("SELECT COUNT(*) FROM Book")
    Integer getBookCount();

    @Query(value = "SELECT (SELECT count(*) as PAID FROM book2user where book_id = ?1 AND type_id = 3) " +
            "+ 0.7 * (SELECT count(*) as CARD FROM book2user where book_id = ?1  AND type_id = 2) " +
            "+ 0.4 * (SELECT count(*) as KEEP FROM book2user where book_id = ?1  AND type_id = 1) as popularity;", nativeQuery = true)
    Double getBookPopularity(Integer bookId);

    @Query(value = "SELECT b.id AS id,b.slug,title,image,(SELECT authors FROM (SELECT GROUP_CONCAT(a.name) AS authors,b.id AS bId FROM book2author ba " +
            "JOIN author a ON a.id = ba.author_id " +
            "JOIN book b ON b.id = ba.book_id GROUP BY book_id) AS t where t.bId = b.id) AS authors,discount,is_bestseller AS isBestseller,price, ROUND(price - (price * discount/100)) AS discountPrice FROM book b " +
            "LEFT JOIN author a ON b.id = a.id " +
            "JOIN book_popularity bp ON bp.book_id = b.id " +
            "ORDER BY bp.popularity DESC ", nativeQuery = true)
    List<BookInit> findPopularBooks(Pageable pageable);

    @Query(value = "select b.id as id,b.slug,title,image, " +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "join book2genre bg on b.id = bg.book_id where bg.genre_id = :genreId " +
            "ORDER BY b.pub_date DESC ", nativeQuery = true)
    List<BookInit> findBooksByGenreId(Pageable pageable, @Param("genreId") Integer genreId);

    @Query(value = "select count(*) from (select b.id as id,b.slug,title,image, " +
            "(select authors from (SELECT group_concat(a.name) as authors,b.id as bId FROM book2author ba join author a on a.id = ba.author_id join book b on b.id = ba.book_id group by book_id) as t where t.bId = b.id) as authors,discount,is_bestseller as isBestseller,price, round(price - (price * discount/100)) as discountPrice " +
            "from book b left join author a on b.id = a.id " +
            "join book2genre bg on b.id = bg.book_id where bg.genre_id = :genreId " +
            "ORDER BY b.pub_date DESC) as count ", nativeQuery = true)
    Integer getCountBooksByGenreId(@Param("genreId") Integer genreId);
}
