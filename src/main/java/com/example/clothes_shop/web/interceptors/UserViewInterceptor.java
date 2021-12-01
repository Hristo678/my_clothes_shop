package com.example.clothes_shop.web.interceptors;

import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.services.OffersService;
import com.example.clothes_shop.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component
public class UserViewInterceptor implements HandlerInterceptor {
    Logger logger = Logger.getLogger(UserViewInterceptor.class.getName());

    private UserService userService;
    private OffersService offersService;

    public UserViewInterceptor(UserService userService, OffersService offersService) {
        this.userService = userService;
        this.offersService = offersService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long offerId = Long.parseLong(request.getRequestURI().split("/")[3]);
        OfferEntity offer = offersService.findById(offerId);
        UserEntity user = offer.getOwner();
        user.setViewsCount(user.getViewsCount() + 1);
        userService.saveUser(user);

        return true;
    }
}
