package com.example.BookApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book2GenreWithCount {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer bookCount;

    private TreeSet<Book2GenreWithCount> list;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book2GenreWithCount that = (Book2GenreWithCount) o;
        return Objects.equals(id, that.id) && Objects.equals(parentId, that.parentId) && Objects.equals(name, that.name) && Objects.equals(bookCount, that.bookCount) && Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, name, bookCount, list);
    }
}
