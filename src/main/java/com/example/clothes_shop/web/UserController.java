package com.example.clothes_shop.web;

import com.example.clothes_shop.models.bindingModels.UserLoginBindingModel;
import com.example.clothes_shop.models.bindingModels.UserRegisterBindingModel;
import com.example.clothes_shop.models.viewModels.SellerViewModel;
import com.example.clothes_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UserService userService;
    private ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/login")
    public String login(){
        return "login";
    }

    @GetMapping("/users/register")
    public String register(){
        return "register";
    }

    @PostMapping("/users/register")
    public String registerPost(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors() || !phoneNumberIsValid(userRegisterBindingModel.getPhoneNumber())){

            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";

        }

        userService.register(userRegisterBindingModel);

        return "redirect:/users/login";
    }

    @PostMapping("/users/login-error")
    public String failedLogin(

            RedirectAttributes attributes
    ) {

        attributes.addFlashAttribute("bad_credentials", true);


        return "redirect:/users/login";
    }

    @GetMapping("/sellers")
    public String getSellers(){
//        List<SellerViewModel> sellers = userService.findAll().stream().filter(u -> u.getOffers().size() > 0).map(u -> {
//            SellerViewModel seller = modelMapper.map(u, SellerViewModel.class);
//            seller.setNumberOfOffers(u.getOffers().size());
//            return seller;
//        }).collect(Collectors.toList());
//        model.addAttribute("sellers", sellers);

        return "sellers";
    }



    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    private boolean phoneNumberIsValid(String phoneNumber) {

        return phoneNumber.charAt(0) == '0' && phoneNumber.charAt(1) == '1' &&
                phoneNumber.length() == 10 && phoneNumber.matches("[0-9+]") && !phoneNumber.matches("\\D");
    }
}
