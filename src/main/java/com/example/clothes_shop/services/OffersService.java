package com.example.clothes_shop.services;

import com.example.clothes_shop.models.bindingModels.OfferAddBindingModel;
import com.example.clothes_shop.models.bindingModels.OfferUpdateBindingModel;
import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.ConditionEnum;
import com.example.clothes_shop.models.enums.GenderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
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

    void addPictures(Long id, List<MultipartFile> pictures) throws IOException;

    void saveOffer(OfferEntity offerEntity);

    void init();

    Page<OfferEntity> findAllByPage(int page, int size);
    Page<OfferEntity> findAllWithKeyword(GenderEnum gender, String name, String description, int page, int size);
    Page<OfferEntity> findAllWithGender(GenderEnum gender, int page, int size);

    Page<OfferEntity> findAllOffersWithCriteria(String gender, String ClotheCondition, String category,
                                                double minPrice, double maxPrice, int page, int size);

}
