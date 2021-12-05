package com.example.clothes_shop.config;

import com.example.clothes_shop.services.OffersService;
import com.example.clothes_shop.services.RoleService;
import com.example.clothes_shop.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

    private UserService userService;
    private RoleService roleService;
    private OffersService offersService;

    public DbInit(UserService userService, RoleService roleService, OffersService offersService) {
        this.userService = userService;
        this.roleService = roleService;
        this.offersService = offersService;
    }


    @Override
    public void run(String... args) throws Exception {

        roleService.init();
        userService.initUsers();
        offersService.init();

    }
}
