package com.example.clothes_shop.repositories;

import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.ConditionEnum;
import com.example.clothes_shop.models.enums.GenderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<OfferEntity, Long> {

    List<OfferEntity> findAllByIdIsGreaterThanAndApprovedIsTrue(long id);
    List<OfferEntity> findAllByOwnerIs(UserEntity owner);
    List<OfferEntity> findAllByApprovedIsTrue();
    List<OfferEntity> findAllByApprovedIsFalse();
    Page<OfferEntity> findAllByApprovedIsTrueAndGenderEqualsAndNameOrDescriptionContains(GenderEnum gender, String name, String description, Pageable pageable);
    Page<OfferEntity> findAllByApprovedIsTrueAndGenderEquals(GenderEnum gender, Pageable pageable);
    Page<OfferEntity> findAllByApprovedIsTrueAndGenderEqualsAndClotheConditionEqualsAndCategoryEqualsAndPriceBetween(GenderEnum gender, ConditionEnum ClotheCondition, CategoryEnum category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<OfferEntity> findAllByApprovedIsTrueAndGenderEqualsAndCategoryEqualsAndPriceBetween(GenderEnum gender, CategoryEnum category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<OfferEntity> findAllByApprovedIsTrueAndGenderEqualsAndClotheConditionEqualsAndPriceBetween(GenderEnum gender, ConditionEnum ClotheCondition, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<OfferEntity> findAllByApprovedIsTrueAndGenderEqualsAndPriceBetween(GenderEnum gender,  BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);


}
