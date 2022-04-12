package com.example.clothes_shop.models.enums;

public enum GenderEnum {

    MALE("Мъжко"), FEMALE("Женско");
    public final String label;

    private GenderEnum(String label) {
        this.label = label;
    }
}
