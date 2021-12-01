package com.example.clothes_shop.web;

import com.example.clothes_shop.models.bindingModels.OfferAddBindingModel;
import com.example.clothes_shop.models.bindingModels.OfferUpdateBindingModel;
import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.GenderEnum;
import com.example.clothes_shop.models.enums.SizeEnum;
import com.example.clothes_shop.models.viewModels.OfferDetailViewModel;
import com.example.clothes_shop.models.viewModels.OfferViewModel;
import com.example.clothes_shop.models.viewModels.UserDetailsView;
import com.example.clothes_shop.services.OffersService;
import com.example.clothes_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OffersController {
    private ModelMapper modelMapper;
    private OffersService offersService;
    private UserService userService;


    public OffersController(ModelMapper modelMapper, OffersService offersService, UserService userService) {
        this.modelMapper = modelMapper;
        this.offersService = offersService;
        this.userService = userService;
    }

    @GetMapping("/offers/add")
    public String offerAdd(Model model) {
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("genders", GenderEnum.values());
        model.addAttribute("size", SizeEnum.values());

        return "offer-add";
    }

    @PostMapping("/offers/add")
    public String offerAddPost(@Valid OfferAddBindingModel offerAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("offerAddBindingModel", offerAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel", bindingResult);

            return "redirect:/offers/add";

        }

        offersService.addOffer(offerAddBindingModel, principal.getName());

        return "redirect:/";
    }

    @Transactional
    @GetMapping("/offers/{gender}")
    public String offers(Model model, @PathVariable String gender) {
        List<OfferViewModel> offers = new ArrayList<>();
        if (gender.equals("ALL")){
            offers = offersService.findAllOffers().stream().map(o -> {
                OfferViewModel offer = modelMapper.map(o, OfferViewModel.class);
                offer.setImageUrl(o.getImagesUrl().stream().findFirst().orElse(null));
                return offer;
            }).collect(Collectors.toList());
        }else if (gender.equals("MEN")){
            offers = offersService.findAllOffers().stream().filter(o -> o.getGender().name().equals("MALE")).map(o -> {
                OfferViewModel offer = modelMapper.map(o, OfferViewModel.class);
                offer.setImageUrl(o.getImagesUrl().stream().findFirst().orElse(null));
                return offer;
            }).collect(Collectors.toList());
        }else {
            offers = offersService.findAllOffers().stream().filter(o -> o.getGender().name().equals("FEMALE")).map(o -> {
                OfferViewModel offer = modelMapper.map(o, OfferViewModel.class);
                offer.setImageUrl(o.getImagesUrl().stream().findFirst().orElse(null));
                return offer;
            }).collect(Collectors.toList());
        }

        model.addAttribute("offers", offers);

        return "offers";
    }

    @Transactional
    @GetMapping("/offers/{id}/details")
    public String details(@PathVariable Long id, Model model, Principal principal) {
        boolean showDeleteAndUpdateButton = false;
        if (offersService.isOwner(principal.getName(), id)){
            showDeleteAndUpdateButton = true;
        }
        OfferEntity offer = offersService.findById(id);
        OfferDetailViewModel offerDetailViewModel = modelMapper.map(offer, OfferDetailViewModel.class);
        offerDetailViewModel.setSellerFirstAndLAstName(offer.getOwner().getFirstName() + " " + offer.getOwner().getLastName());
        offerDetailViewModel.setImagesUrl(offer.getImagesUrl());


        model.addAttribute("offer", offerDetailViewModel);
        model.addAttribute("showDeleteAndUpdateButton", showDeleteAndUpdateButton);



        return "details";
    }

    @Transactional
    @GetMapping("/profile")
    public String myOffers(Principal principal, Model model) {
        UserEntity owner = userService.findByUsername(principal.getName());
        UserDetailsView userDetailsView = modelMapper.map(owner, UserDetailsView.class);
        List<OfferViewModel> offers = offersService.findAllByOwner(owner).stream().map(o -> {
            OfferViewModel offer = modelMapper.map(o, OfferViewModel.class);
            offer.setImageUrl(o.getImagesUrl().stream().findFirst().orElse(null));
            return offer;
        }).collect(Collectors.toList());
        model.addAttribute("offers", offers);
        model.addAttribute("user", userDetailsView);
        return "profile";
    }

    @Transactional
    @GetMapping("/seller/details/{offerId}")
    public String sellerDetails(@PathVariable Long offerId, Model model){
        OfferEntity offerEntity = offersService.findById(offerId);
        UserEntity owner = offerEntity.getOwner();
        UserDetailsView userDetailsView = modelMapper.map(owner, UserDetailsView.class);
        List<OfferViewModel> offers = offersService.findAllByOwner(owner).stream().map(o -> {
            OfferViewModel offer = modelMapper.map(o, OfferViewModel.class);
            offer.setImageUrl(o.getImagesUrl().stream().findFirst().orElse(null));
            return offer;
        }).collect(Collectors.toList());

        model.addAttribute("offers", offers);
        model.addAttribute("user", userDetailsView);

        return "profile";
    }

    @DeleteMapping("/offers/{id}/delete")
    @PreAuthorize("@offersServiceImpl.isOwner(#user.name, #id)")
    public String deleteOffer(@PathVariable Long id, Principal user){

        offersService.deleteOffer(id);
        return "redirect:/offers/ALL";
    }

    @GetMapping("/offer/{id}/update")
    public String update(@PathVariable Long id, Model model){
        OfferEntity offerEntity = offersService.findById(id);
        OfferUpdateBindingModel offerUpdateBindingModel = modelMapper.map(offerEntity, OfferUpdateBindingModel.class);
        model.addAttribute("offer", offerUpdateBindingModel);
        model.addAttribute("genders", GenderEnum.values());
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("sizes", SizeEnum.values());
        return "update";
    }

    @PatchMapping("offer/{id}/update")
    @PreAuthorize("@offersServiceImpl.isOwner(#user.name, #id)")
    public String update(@PathVariable Long id ,
                         @Valid OfferUpdateBindingModel offer,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, Principal user){

        if (bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("offer", offer);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offer", bindingResult);

            return "redirect:/offer/" + id + "/update";

        }
        offersService.update(offer);
        return "redirect:/offers/" + id + "/details";

    }

    @PostMapping("/offers/{id}/add-picture")
    @PreAuthorize("@offersServiceImpl.isOwner(#user.name, #id)")
    public String addPicture(@PathVariable Long id , Principal user, Model model, @RequestParam("picture") MultipartFile file) throws IOException {

        offersService.addPucture(id, file);
        return "redirect:/offers/"+ id + "/details";
    }


    @ModelAttribute
    public OfferAddBindingModel offerAddBindingModel() {
        return new OfferAddBindingModel();
    }
}
