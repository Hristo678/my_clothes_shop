package com.example.clothes_shop.models.entities;

import com.example.clothes_shop.models.enums.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "roles")
public class RoleEntity extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
