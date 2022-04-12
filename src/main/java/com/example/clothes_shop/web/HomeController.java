package com.example.clothes_shop.web;

import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.viewModels.OfferViewModel;
import com.example.clothes_shop.services.OffersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Controller
public class HomeController {

    private OffersService offersService;
    private ModelMapper modelMapper;

    public HomeController(OffersService offersService, ModelMapper modelMapper) {
        this.offersService = offersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String home(Model model){
        List<OfferViewModel> offers = offersService.findTheNewestOffers().stream().map(o -> {
            OfferViewModel offer = modelMapper.map(o, OfferViewModel.class);
            List<String> imageUrls = getImageUrl(o);
            offer.setImageUrl(imageUrls.stream().findFirst().orElse(null));
            return offer;
        }).collect(Collectors.toList());
        model.addAttribute("offers", offers);

        return "index";
    }

    private List<String> getImageUrl(OfferEntity o) {
        return o.getImagesUrl().stream().map(offer -> offer.getUrl()).collect(Collectors.toList());
    }

    @GetMapping("/about")
    public ModelAndView about(ModelAndView modelAndView){
        modelAndView.setViewName("about");

        return modelAndView;
    }
}
