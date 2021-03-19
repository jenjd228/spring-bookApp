package com.example.BookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInitDTO {

    private Integer id;

    private String slug;

    private String title;

    private String image;

    private String authors;

    private Byte discount;

    private boolean isBestseller;

    private Byte rating;

    private String status;

    private Integer price;

    private Integer discountPrice;

    public void convertByteToBooleanIsBestseller(Byte isBestseller){
        this.isBestseller = isBestseller == 1;
    }

}
