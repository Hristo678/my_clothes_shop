package com.example.clothes_shop.models.viewModels;

import com.example.clothes_shop.models.enums.CategoryEnum;

import java.math.BigDecimal;

public class OfferViewModel {

    private long id;
    private String imageUrl;
    private String name;
    private BigDecimal price;
    private CategoryEnum category;

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
}
