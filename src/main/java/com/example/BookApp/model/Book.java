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
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "pub_date")
    private LocalDateTime pubDate;

    @Column(nullable = false, name = "is_bestseller")
    private Byte isBestseller;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Lob
    private String text;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private Byte discount;
}
