package com.example.BookApp.dto;

import com.example.BookApp.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSlugDTO {

    private Integer id;

    private String slug;

    private String title;

    private String image;

    private Set<AuthorOnlyNameAndIdDTO> authors;

    private Set<CommentDTO> comments;

    private boolean isBestseller;

    private Byte rating;

    private String status;

    private BigDecimal price;

    private Integer discountPrice;

    private String description;

    private Set<Tag> tagSet;

    private Integer ratingValueCount;

    private Integer fiveQuantity;

    private Integer fourQuantity;

    private Integer threeQuantity;

    private Integer twoQuantity;

    private Integer oneQuantity;

    private Integer commentsCount;

    public void setIsBestsellerByConvertingByteToBoolean(Byte isBestseller) {
        this.isBestseller = isBestseller == 1;
    }

    public void setDiscountPriceByDiscount(BigDecimal price, Byte discountPrice) {
        int priceIntValue = price.intValue();
        this.discountPrice = priceIntValue - Math.round(priceIntValue * discountPrice / 100);
    }
}
