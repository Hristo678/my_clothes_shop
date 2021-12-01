package com.example.clothes_shop.web;

import com.example.clothes_shop.models.bindingModels.UserLoginBindingModel;
import com.example.clothes_shop.models.bindingModels.UserRegisterBindingModel;
import com.example.clothes_shop.services.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
