package com.example.clothes_shop.web.interceptors;

import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.services.OffersService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component
public class OffersViewInterceptor implements HandlerInterceptor {

    private OffersService offersService;
    static Logger logger = Logger.getLogger(OffersViewInterceptor.class.getName());

    public OffersViewInterceptor(OffersService offersService) {
        this.offersService = offersService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long offerId = Long.parseLong(request.getRequestURI().split("/")[2]);
        OfferEntity offer = offersService.findById(offerId);
        offer.setViewsCount(offer.getViewsCount() + 1);
        offersService.saveOffer(offer);

        return true;
    }
}
