package com.example.clothes_shop.services;

import com.example.clothes_shop.models.entities.RoleEntity;
import com.example.clothes_shop.models.enums.RoleEnum;

public interface RoleService {

    public void init();
    public RoleEntity findByRole(RoleEnum role);
}
