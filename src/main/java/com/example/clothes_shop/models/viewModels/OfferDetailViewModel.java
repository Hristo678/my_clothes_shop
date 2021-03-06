package com.example.clothes_shop.models.viewModels;

import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.ConditionEnum;
import com.example.clothes_shop.models.enums.GenderEnum;
import com.example.clothes_shop.models.enums.SizeEnum;
import com.example.clothes_shop.services.cloudinary.CloudinaryImage;

import java.math.BigDecimal;
import java.util.List;

public class OfferDetailViewModel {

    private Long id;
    private List<CloudinaryImage> imagesUrl;
    private String name;
    private BigDecimal price;
    private CategoryEnum category;
    private String description;
    private List<SizeEnum> sizes;
    private GenderEnum gender;
    private ConditionEnum clotheCondition;
    private String sellerFirstAndLAstName;
    private int viewsCount;

    public String getSellerFirstAndLAstName() {
        return sellerFirstAndLAstName;
    }

    public void setSellerFirstAndLAstName(String sellerFirstAndLAstName) {
        this.sellerFirstAndLAstName = sellerFirstAndLAstName;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CloudinaryImage> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<CloudinaryImage> imagesUrl) {
        this.imagesUrl = imagesUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SizeEnum> getSizes() {
        return sizes;
    }

    public void setSizes(List<SizeEnum> sizes) {
        this.sizes = sizes;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public ConditionEnum getClotheCondition() {
        return clotheCondition;
    }

    public void setClotheCondition(ConditionEnum clotheCondition) {
        this.clotheCondition = clotheCondition;
    }
}
