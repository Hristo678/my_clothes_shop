package com.example.clothes_shop.services.impl;

import com.example.clothes_shop.models.entities.RoleEntity;
import com.example.clothes_shop.models.enums.RoleEnum;
import com.example.clothes_shop.repositories.RoleRepository;
import com.example.clothes_shop.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void init() {
        if (roleRepository.count() == 0){
            RoleEntity admin = new RoleEntity();
            admin.setRole(RoleEnum.ADMIN);
            RoleEntity user = new RoleEntity();
            user.setRole(RoleEnum.USER);

            roleRepository.save(admin);
            roleRepository.save(user);
        }

    }

    @Override
    public RoleEntity findByRole(RoleEnum role) {
        return roleRepository.findByRole(role);
    }
}
