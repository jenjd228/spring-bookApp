package com.example.BookApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_id2rating_value")
public class BookId2RatingValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name = "user_id")
    private Integer userId;

    @Column(nullable = false,name = "book_id")
    private Integer bookId;

    @Column(nullable = false)
    private Byte value;
}
