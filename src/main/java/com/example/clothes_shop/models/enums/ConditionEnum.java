package com.example.clothes_shop.models.enums;

public enum ConditionEnum {
    NEW ("Ново"),
    USED ("Използвано"),
    ALL("Всичко");

    public final String label;

    private ConditionEnum(String label) {
        this.label = label;
    }

}
