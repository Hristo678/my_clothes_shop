package com.example.clothes_shop.services.impl;

import com.example.clothes_shop.models.bindingModels.UserRegisterBindingModel;
import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.models.enums.RoleEnum;
import com.example.clothes_shop.repositories.UserRepository;
import com.example.clothes_shop.services.RoleService;
import com.example.clothes_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private ModelMapper mapper;
    private UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper mapper, @Qualifier("clothesShopUserDetailsService") UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void initUsers() {
        if (userRepository.count() == 0){
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail("123@email.bg");
            userEntity.setFirstName("Admin");
            userEntity.setLastName("Adminov");
            userEntity.setPassword(passwordEncoder.encode("admin"));
            userEntity.setRoles(List.of(roleService.findByRole(RoleEnum.ADMIN), roleService.findByRole(RoleEnum.USER)));
            userEntity.setUsername("admin");
            userEntity.setPhoneNumber("0883656789");
            userRepository.save(userEntity);
        }

    }

    @Override
    public void register(UserRegisterBindingModel userRegisterBindingModel) {

            UserEntity user = mapper.map(userRegisterBindingModel, UserEntity.class);
            user.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
            user.setRoles(List.of(roleService.findByRole(RoleEnum.USER)));
            userRepository.save(user);


    }

    @Override
    public UserEntity findByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserEntity findById(long userId) {

        return userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
