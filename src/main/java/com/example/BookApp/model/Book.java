package com.example.BookApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private Byte discount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tag2book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<Tag> tagSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book2author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    Set<Author> authors;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Set<BookReview> comments;

    @Formula("(select e.value from book_id2rating e where e.book_id = id)")
    private Byte rating;

    @Formula("(select count(*) from book_id2rating_value e where e.book_id = id)")
    private Integer ratingValueCount;

    @Formula("(select count(*) from book_id2rating_value e where e.book_id = id and e.value = 5)")
    private Integer fiveQuantity;

    @Formula("(select count(*) from book_id2rating_value e where e.book_id = id and e.value = 4)")
    private Integer fourQuantity;

    @Formula("(select count(*) from book_id2rating_value e where e.book_id = id and e.value = 3)")
    private Integer threeQuantity;

    @Formula("(select count(*) from book_id2rating_value e where e.book_id = id and e.value = 2)")
    private Integer twoQuantity;

    @Formula("(select count(*) from book_id2rating_value e where e.book_id = id and e.value = 1)")
    private Integer oneQuantity;



}
