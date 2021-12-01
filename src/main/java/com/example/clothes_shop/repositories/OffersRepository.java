package com.example.clothes_shop.repositories;

import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<OfferEntity, Long> {

    List<OfferEntity> findAllByIdIsGreaterThan(long id);
    List<OfferEntity> findAllByOwnerIs(UserEntity owner);
}
