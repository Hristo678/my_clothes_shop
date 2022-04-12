package com.example.clothes_shop.models.enums;

public enum CategoryEnum {

    JACKET("Яке"), SHIRT("Тениска"), JEANS("Дънки"), SWEATSHIRTS("Блуза"), HATS("Шапка");

    public final String label;

    private CategoryEnum(String label) {
        this.label = label;
    }
}
