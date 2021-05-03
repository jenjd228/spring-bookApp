package com.example.BookApp.repository;

import com.example.BookApp.model.Book2Genre;
import com.example.BookApp.model.Book2GenreWithCountInterface;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book2GenreRepository extends CrudRepository<Book2Genre, Integer> {

    @Query(value = "select g.id,g.parent_id,g.name, (select count(*) from book2genre bg where bg.genre_id = g.id) as bookCount " +
                   "from genre g where g.parent_id is null ",nativeQuery = true)
    List<Book2GenreWithCountInterface> findBook2GenreWhereParentIdIsNull();

    @Query(value = "select g.id,g.parent_id as parentId,g.name, (select count(*) from book2genre bg where bg.genre_id = g.id) as bookCount " +
            "from genre g where g.parent_id is not null order by g.parent_id ",nativeQuery = true) //Сортировка нужна чтобы не усложнять добавление в treeMap. Так как при тких действиях для каждого элемента будет родитель в первом проходе цикла (по дочерним элементам).
    List<Book2GenreWithCountInterface> findBook2GenreWhereParentIdIsNotNull();
}
