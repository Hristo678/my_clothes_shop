package com.example.clothes_shop.services;

import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClothesShopUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    private final UserRepository userRepository;

    public ClothesShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("Username " + username + " was not found!"));

        return mapToUserDetailsService(user);
    }

    private UserDetails mapToUserDetailsService(UserEntity user) {

        List<GrantedAuthority> authorities = user.getRoles().stream().
                map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

    }
}
