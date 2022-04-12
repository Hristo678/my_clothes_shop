package com.example.clothes_shop.models.entities;

import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.ConditionEnum;
import com.example.clothes_shop.models.enums.GenderEnum;
import com.example.clothes_shop.models.enums.SizeEnum;
import com.example.clothes_shop.services.cloudinary.CloudinaryImage;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity(name = "offers")
public class OfferEntity extends BaseEntity {


    private String name;
    private BigDecimal price;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    private UserEntity owner;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<SizeEnum> sizes;
    @Enumerated(EnumType.STRING)
    private ConditionEnum clotheCondition;
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<CloudinaryImage> imagesUrl;
    private int viewsCount;
    private boolean approved;

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

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public List<SizeEnum> getSizes() {
        return sizes;
    }

    public void setSizes(List<SizeEnum> sizes) {
        this.sizes = sizes;
    }

    public ConditionEnum getClotheCondition() {
        return clotheCondition;
    }

    public void setClotheCondition(ConditionEnum clotheCondition) {
        this.clotheCondition = clotheCondition;
    }

    public List<CloudinaryImage> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<CloudinaryImage> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void addSize(SizeEnum size){
        this.sizes.add(size);
    }
}
