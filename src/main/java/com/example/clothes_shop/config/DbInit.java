package com.example.clothes_shop.config;

import com.example.clothes_shop.services.RoleService;
import com.example.clothes_shop.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

    private UserService userService;
    private RoleService roleService;

    public DbInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public void run(String... args) throws Exception {
        roleService.init();
        userService.initUsers();
    }
}
