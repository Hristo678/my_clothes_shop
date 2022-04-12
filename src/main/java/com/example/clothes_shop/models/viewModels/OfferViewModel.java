package com.example.clothes_shop.models.viewModels;

import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.ConditionEnum;

import java.math.BigDecimal;

public class OfferViewModel {

    private long id;
    private String imageUrl;
    private String name;
    private BigDecimal price;
    private CategoryEnum category;
    private ConditionEnum clotheCondition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public ConditionEnum getClotheCondition() {
        return clotheCondition;
    }

    public void setClotheCondition(ConditionEnum clotheCondition) {
        this.clotheCondition = clotheCondition;
    }
}
