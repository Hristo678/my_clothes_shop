package com.example.clothes_shop.repositories;

import com.example.clothes_shop.models.entities.RoleEntity;
import com.example.clothes_shop.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(RoleEnum role);
}
