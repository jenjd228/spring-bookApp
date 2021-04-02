package com.example.BookApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book2genre")
public class Book2Genre implements Serializable {

    @EmbeddedId
    private BookId2genreKey key;

    @Embeddable
    @Data
    public static class BookId2genreKey implements Serializable {

        @Column(name = "book_id",nullable = false)
        private Integer bookId;

        @Column(name = "genre_id",nullable = false)
        private Integer genreId;
    }
}
