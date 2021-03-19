package com.example.BookApp.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_popularity")
public class BookPopularity implements Serializable{

    @EmbeddedId
    private BookId2popularityKey key;

    @Embeddable
    @Data
    public static class BookId2popularityKey implements Serializable {

        @Column(name = "book_id")
        private Integer bookId;

        @Column(nullable = false)
        private Double popularity;
    }


}
