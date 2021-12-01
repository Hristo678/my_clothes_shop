package com.example.clothes_shop.services.impl;

import com.example.clothes_shop.models.bindingModels.OfferAddBindingModel;
import com.example.clothes_shop.models.bindingModels.OfferUpdateBindingModel;
import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.models.enums.CategoryEnum;
import com.example.clothes_shop.models.enums.GenderEnum;
import com.example.clothes_shop.models.enums.RoleEnum;
import com.example.clothes_shop.models.enums.SizeEnum;
import com.example.clothes_shop.repositories.OffersRepository;
import com.example.clothes_shop.repositories.UserRepository;
import com.example.clothes_shop.services.OffersService;
import com.example.clothes_shop.services.UserService;
import com.example.clothes_shop.services.cloudinary.CloudinaryImage;
import com.example.clothes_shop.services.cloudinary.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class OffersServiceImpl implements OffersService {

    private OffersRepository offersRepository;
    private final CloudinaryService cloudinaryService;
    private ModelMapper modelMapper;
    private UserService userService;
    private UserRepository userRepository;

    public OffersServiceImpl(OffersRepository offersRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper, UserService userService, UserRepository userRepository) {
        this.offersRepository = offersRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.userRepository = userRepository;
    }



    @Transactional
    @Override
    public List<OfferEntity> findAllOffers() {
        return offersRepository.findAll();
    }

    @Override
    public void addOffer(OfferAddBindingModel offerAddBindingModel, String username) throws IOException {

        CloudinaryImage cloudinaryImage = cloudinaryService.upload(offerAddBindingModel.getPicture());
        OfferEntity offer = modelMapper.map(offerAddBindingModel, OfferEntity.class);
        offer.setCategory(CategoryEnum.valueOf(offerAddBindingModel.getCategory()));
        offer.setGender(GenderEnum.valueOf(offerAddBindingModel.getGender()));
        offer.setSize(SizeEnum.valueOf(offerAddBindingModel.getSize()));
        offer.setOwner(userService.findByUsername(username));
        offer.setImagesUrl(List.of(cloudinaryImage.getUrl()));

        offersRepository.save(offer);


    }

    @Override
    @Transactional
    public List<OfferEntity> findTheNewestOffers() {
        long idForTheLastSixOffers = offersRepository.count() - 6 > 0 ? offersRepository.count() -1 : 0;

        return offersRepository.findAllByIdIsGreaterThan(idForTheLastSixOffers);
    }

    @Override
    public OfferEntity findById(Long id) {
        return offersRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<OfferEntity> findAllByOwner(UserEntity owner) {
        return offersRepository.findAllByOwnerIs(owner);
    }

    @Override
    public boolean isOwner(String name, Long id) {

        UserEntity user = userRepository.findByUsername(name).orElse(null);
        OfferEntity offer = offersRepository.findById(id).orElse(null);
        if (user.getRoles().contains(RoleEnum.ADMIN)) {
            return true;
        }
        if (offer.getOwner().getUsername().equals(user.getUsername())) {
            return true;
        }

        return false;
    }

    @Override
    public void deleteOffer(Long id) {
        offersRepository.delete(Objects.requireNonNull(offersRepository.findById(id).orElse(null)));
    }

    @Override
    public void update(OfferUpdateBindingModel offer) {
        OfferEntity offerEntity = offersRepository.findById(offer.getId()).orElse(null);
        offerEntity.setPrice(offer.getPrice());
        offerEntity.setGender(offer.getGender());
        offerEntity.setSize(offer.getSize());
        offerEntity.setCategory(offer.getCategory());
        offerEntity.setDescription(offer.getDescription());
        offerEntity.setName(offer.getName());
        offersRepository.save(offerEntity);
    }

    @Transactional
    @Override
    public void addPucture(Long id, MultipartFile picture) throws IOException {
        CloudinaryImage cloudinaryImage = cloudinaryService.upload(picture);
        OfferEntity offerEntity = offersRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        offerEntity.getImagesUrl().add(cloudinaryImage.url);

    }

    @Override
    public void saveOffer(OfferEntity offerEntity){
        offersRepository.save(offerEntity);
    }
}
