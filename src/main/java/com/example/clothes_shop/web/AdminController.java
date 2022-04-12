package com.example.clothes_shop.web;

import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.viewModels.OfferViewModel;
import com.example.clothes_shop.services.OffersService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private OffersService offersService;
    private ModelMapper modelMapper;

    public AdminController(OffersService offersService, ModelMapper modelMapper) {
        this.offersService = offersService;
        this.modelMapper = modelMapper;
    }
    @Transactional
    @GetMapping("/admin")
    public String admin(Model model){
        List<OfferViewModel> offers = offersService.findAllNotApprovedOffers()
                .stream().map(o -> {
                    OfferViewModel offerViewModel = modelMapper.map(o, OfferViewModel.class);
                    List<String> imageUrls = getImageUrl(o);
                    offerViewModel.setImageUrl(imageUrls.stream().findFirst().orElse(null));
                    return offerViewModel;
                })
                .collect(Collectors.toList());

        model.addAttribute("offers", offers);

        return "admin";
    }
    private List<String> getImageUrl(OfferEntity o) {
        return o.getImagesUrl().stream().map(offer -> offer.getUrl()).collect(Collectors.toList());
    }

    @PostMapping("/approve/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveOffer(@PathVariable Long id, Principal user){
        OfferEntity offer = offersService.findById(id);
        offer.setApproved(true);
        offersService.saveOffer(offer);
        return "redirect:/offers/"+id+"/details";
    }
}
