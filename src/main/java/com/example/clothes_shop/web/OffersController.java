package com.example.clothes_shop.web;

import com.example.clothes_shop.models.bindingModels.OfferAddBindingModel;
import com.example.clothes_shop.models.bindingModels.OfferUpdateBindingModel;
import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.ConditionEnum;
import com.example.clothes_shop.models.enums.GenderEnum;
import com.example.clothes_shop.models.enums.SizeEnum;
import com.example.clothes_shop.models.viewModels.OfferDetailViewModel;
import com.example.clothes_shop.models.viewModels.OfferViewModel;
import com.example.clothes_shop.models.viewModels.UserDetailsView;
import com.example.clothes_shop.services.OffersService;
import com.example.clothes_shop.services.UserService;
import com.example.clothes_shop.services.cloudinary.CloudinaryImage;
import com.example.clothes_shop.services.cloudinary.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OffersController {
    private ModelMapper modelMapper;
    private OffersService offersService;
    private UserService userService;
    private CloudinaryService cloudinaryService;


    public OffersController(ModelMapper modelMapper, OffersService offersService, UserService userService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.offersService = offersService;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/offers/add")
    public String offerAdd(Model model) {
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("genders", GenderEnum.values());
        model.addAttribute("size", SizeEnum.values());
        model.addAttribute("conditions", ConditionEnum.values());

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
    @GetMapping("/offers/{gender}/{page}/{size}")
    public String offers(Model model, @PathVariable String gender, @RequestParam(value = "keyword", required = false, defaultValue = "blanc")
            String keyword, @PathVariable int page, @PathVariable int size) {
        List<OfferViewModel> offers;
        Page<OfferEntity> offersEntities;
        if (keyword.equals("blanc")){
            offersEntities = offersService.findAllWithGender(GenderEnum.valueOf(gender), page, size);
            offers = offersEntities.stream()
                    .map(this::mapToOfferViewModel).collect(Collectors.toList());
            keyword = "";
        }else {
            offersEntities = offersService.findAllWithKeyword(GenderEnum.valueOf(gender), keyword, keyword, page, size);
            offers = offersEntities.stream()
                    .map(this::mapToOfferViewModel).collect(Collectors.toList());
        }


        model.addAttribute("offers", offers);
        model.addAttribute("clothConditions", ConditionEnum.values());
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("currentGender", gender);
        model.addAttribute("currentCategory", "ALL");
        model.addAttribute("currentClotheCondition", "ALL");
        model.addAttribute("minPrice", 0);
        model.addAttribute("maxPrice", 10000);
        model.addAttribute("currentKeyword", keyword);
        model.addAttribute("numberOfPages", offersEntities.getTotalPages());
        model.addAttribute("currentPage", page);

        return "offers";
    }

    @Transactional
    @GetMapping("/offers/{gender}/{clotheCondition}/{category}/{minPrice}/{maxPrice}/{page}/{size}")
    public String offersWithCriteria(Model model, @PathVariable String gender, @PathVariable String clotheCondition,
                                     @PathVariable String category, @PathVariable double minPrice,
                                     @PathVariable double maxPrice, @PathVariable int page, @PathVariable int size) {
        Page<OfferEntity> offersEntities = offersService.findAllOffersWithCriteria(gender, clotheCondition,
                category, minPrice, maxPrice , page, size);
        List<OfferViewModel> offers = offersEntities.stream()
                    .map(this::mapToOfferViewModel).collect(Collectors.toList());


        model.addAttribute("offers", offers);
        model.addAttribute("clothConditions", ConditionEnum.values());
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("currentGender", gender);
        model.addAttribute("currentClotheCondition", clotheCondition);
        model.addAttribute("currentCategory", category);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("numberOfPages", offersEntities.getTotalPages());
        model.addAttribute("currentPage", page);



        return "offers";
    }

    @Transactional
    @PostMapping("/offers/{gender}/{clotheCondition}/{category}/{page}/{size}")
    public String offersWithPrice( @PathVariable String gender, @PathVariable String clotheCondition,
                                     @PathVariable String category, @PathVariable int page, @PathVariable int size,
                                   @RequestParam(value = "minimalPrice", required = false, defaultValue = "0") String minimalPrice,
                                   @RequestParam(value = "maximalPrice", required = false, defaultValue = "10000") String maximalPrice) {
        try {
            Double.parseDouble(minimalPrice);
        }catch (Exception ex){
            minimalPrice = "0";
        }
        try {
            Double.parseDouble(maximalPrice);
        }catch (NumberFormatException nfe){
            maximalPrice = "10000";
        }

        String redirectUrl = String.format("/offers/%s/%s/%s/%s/%s/%s/%s", gender, clotheCondition, category, minimalPrice, maximalPrice, page, size);

        return "redirect:"+ redirectUrl;
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
        offerDetailViewModel.setClotheCondition(offer.getClotheCondition());
        String sizes = offer.getSizes().stream().map(Enum::toString).collect(Collectors.joining(" "));


        model.addAttribute("offer", offerDetailViewModel);
        model.addAttribute("sizes", sizes);
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
            List<String> imageUrls = getImageUrl(o);
            offer.setImageUrl(imageUrls.stream().findFirst().orElse(null));
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
            List<String> imageUrls = getImageUrl(o);
            offer.setImageUrl(imageUrls.stream().findFirst().orElse(null));
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

    @Transactional
    @GetMapping("/offer/{id}/update")
    public String update(@PathVariable Long id, Model model){
        OfferEntity offerEntity = offersService.findById(id);
        OfferUpdateBindingModel offerUpdateBindingModel = modelMapper.map(offerEntity, OfferUpdateBindingModel.class);
        offerUpdateBindingModel.setSizes(offerEntity.getSizes());
        model.addAttribute("offer", offerUpdateBindingModel);
        model.addAttribute("genders", GenderEnum.values());
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("sizes", SizeEnum.values());
        model.addAttribute("conditions", ConditionEnum.values());
        return "update";
    }

    @PatchMapping("offer/{id}/update")
    @PreAuthorize("@offersServiceImpl.isOwner(#user.name, #id)")
    public String update(@Valid OfferUpdateBindingModel offer,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, @PathVariable Long id , Principal user){

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
    public String addPicture(@PathVariable Long id , Principal user, Model model, @RequestParam("pictures") List<MultipartFile> files) throws IOException {
        List<MultipartFile> pict = files;
        offersService.addPictures(id, files);
        return "redirect:/offers/"+ id + "/details";
    }

    @PostMapping("/offers/image/delete/{publicId}")
    public String deleteImage(@PathVariable String publicId){
        String id = publicId;
            cloudinaryService.destroy(publicId);
            cloudinaryService.deleteImage(publicId);
            return "redirect:/";
    }

    @PostMapping("/slectedOffers")
    public String getSelectedOffers(OfferViewModel offer){
        OfferViewModel offerView = offer;
        System.out.println();
        return "redirect:/offers/add";
    }




    @ModelAttribute
    public OfferAddBindingModel offerAddBindingModel() {
        return new OfferAddBindingModel();
    }



    private OfferViewModel mapToOfferViewModel(OfferEntity o){
        OfferViewModel offer = modelMapper.map(o, OfferViewModel.class);
        List<String> imageUrls = getImageUrl(o);
        offer.setImageUrl(imageUrls.stream().findFirst().orElse(null));
        return offer;
    }

    private List<String> getImageUrl(OfferEntity o) {
        return o.getImagesUrl().stream().map(CloudinaryImage::getUrl).collect(Collectors.toList());
    }
}
