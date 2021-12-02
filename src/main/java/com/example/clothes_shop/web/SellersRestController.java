package com.example.clothes_shop.web;

import com.example.clothes_shop.models.viewModels.SellerViewModel;
import com.example.clothes_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SellersRestController {
    private UserService userService;
    private ModelMapper modelMapper;

    public SellersRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @GetMapping("/api/sellers")
    public ResponseEntity<List<SellerViewModel>> getAllSellers(){
                List<SellerViewModel> sellers = userService.findAll().stream().filter(u -> u.getOffers().size() > 0).map(u -> {
            SellerViewModel seller = modelMapper.map(u, SellerViewModel.class);
            seller.setNumberOfOffers(u.getOffers().size());
            seller.setOfferId(u.getOffers().get(0).getId());
            return seller;
        }).sorted((s1, s2) -> Integer.compare(s2.getNumberOfOffers(), s1.getNumberOfOffers())).collect(Collectors.toList());
                return ResponseEntity.of(Optional.of(sellers));
    }
}
