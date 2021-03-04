package com.example.BookApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book2author")
public class Book2Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name = "book_id")
    private Integer bookId;

    @Column(nullable = false,name = "author_id")
    private Integer authorId;

    @Column(nullable = false, columnDefinition = "int default 0",name = "sort_index")
    private Integer sortIndex;


}
