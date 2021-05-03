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
@Table(name = "tag2book")
public class Tag2Book {

    @EmbeddedId
    private BookId2TagId key;

    @Embeddable
    @Data
    public static class BookId2TagId implements Serializable {

        @Column(name = "book_id",nullable = false)
        private Integer bookId;

        @Column(name = "tag_id",nullable = false)
        private Integer tagID;
    }
}
