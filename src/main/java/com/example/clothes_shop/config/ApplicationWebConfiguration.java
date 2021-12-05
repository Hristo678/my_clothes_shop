package com.example.clothes_shop.config;

import com.example.clothes_shop.web.interceptors.OffersViewInterceptor;
import com.example.clothes_shop.web.interceptors.UserViewInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {
    private final OffersViewInterceptor offersViewInterceptor;
    private final UserViewInterceptor userViewInterceptor;

    public ApplicationWebConfiguration(OffersViewInterceptor offersViewInterceptor, UserViewInterceptor userViewInterceptor) {
        this.offersViewInterceptor = offersViewInterceptor;
        this.userViewInterceptor = userViewInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(offersViewInterceptor).addPathPatterns("/offers/{id}/details");
        registry.addInterceptor(userViewInterceptor).addPathPatterns("/seller/details/{offerId}");
    }
}
