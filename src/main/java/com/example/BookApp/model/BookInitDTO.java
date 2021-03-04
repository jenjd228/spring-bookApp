package com.example.BookApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInitDTO {

    private Integer bookId;

    private String title;

    private String name;

    private Integer price;

    private long priceNew;

    private Byte discount;

    public void setPriceNewByDiscount(){
        this.priceNew = Math.round(this.price * (this.discount / 100.0));
    }
}
