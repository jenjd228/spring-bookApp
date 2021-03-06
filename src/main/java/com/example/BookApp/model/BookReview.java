package com.example.BookApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_review")
public class BookReview implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name = "book_id")
    private Integer bookId;

    @Column(nullable = false,name = "user_id")
    private Integer userId;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    @Lob
    private String text;
}
