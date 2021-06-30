package com.example.BookApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

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

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;

    @Formula("(select count(*) from book_review_like e where e.value = 1 and e.id = id)")
    private Integer likeCount;

    @Formula("(select count(*) from book_review_like e where e.value = -1 and e.id = id)")
    private Integer dislikeCount;
}
