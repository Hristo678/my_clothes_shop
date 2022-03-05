package com.example.clothes_shop.models.bindingModels;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class OfferAddBindingModel {

    @NotNull
    @Size(min = 3, max = 20)
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @Size(min = 5, max = 50)
    private String description;
    @NotNull
    private String category;
    @NotNull
    private String gender;
    @NotNull
    private String size;
    @NotNull
    private List<MultipartFile> pictures;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public void setPictures(List<MultipartFile> picture) {
        this.pictures = picture;
    }
}
