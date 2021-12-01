package com.example.clothes_shop.services;

import com.example.clothes_shop.models.bindingModels.UserRegisterBindingModel;
import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;

import java.util.List;

public interface UserService {

    public void initUsers();
    public void register(UserRegisterBindingModel userRegisterBindingModel);
    UserEntity findByUsername(String username);

    UserEntity findById(long userId);

    void saveUser(UserEntity user);

    List<UserEntity> findAll();
}
