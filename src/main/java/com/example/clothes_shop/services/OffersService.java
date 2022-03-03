package com.example.clothes_shop.services;

import com.example.clothes_shop.models.bindingModels.OfferAddBindingModel;
import com.example.clothes_shop.models.bindingModels.OfferUpdateBindingModel;
import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OffersService {


    List<OfferEntity> findAllOffers();

    List<OfferEntity> findAllNotApprovedOffers();

    void addOffer(OfferAddBindingModel offerAddBindingModel, String username) throws IOException;

    List<OfferEntity> findTheNewestOffers();


    OfferEntity findById(Long id);

    List<OfferEntity> findAllByOwner(UserEntity owner);

    boolean isOwner(String name, Long id);

    void deleteOffer(Long id);

    void update(OfferUpdateBindingModel offer);

    void addPucture(Long id, MultipartFile picture) throws IOException;

    void saveOffer(OfferEntity offerEntity);

    void init();

}
