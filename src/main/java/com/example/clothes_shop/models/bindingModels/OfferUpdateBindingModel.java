package com.example.clothes_shop.models.bindingModels;

import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.ConditionEnum;
import com.example.clothes_shop.models.enums.GenderEnum;
import com.example.clothes_shop.models.enums.SizeEnum;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class OfferUpdateBindingModel {

    private long id;
    @NotNull
    @Size(min = 3, max = 20)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 5, max = 200)
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    private CategoryEnum category;
    @NotNull
    private GenderEnum gender;
    @NotNull
    private List<SizeEnum> sizes;
    @NotNull
    private ConditionEnum clotheCondition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
