package com.example.BookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostponedBooksDTO {

    private Integer bookId;

    private String image;

    private String title;

    private Integer price;

    private Byte rating;

    private AuthorOnlyNameAndIdDTO author;

    private Integer discountPrice;

}
