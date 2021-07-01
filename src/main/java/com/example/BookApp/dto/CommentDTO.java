package com.example.BookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Integer id;

    private UserOnlyNameAndIdDTO user;

    private String text;

    private String time;

    private int userRating;

    private Integer likeCount;

    private Integer dislikeCount;

}
