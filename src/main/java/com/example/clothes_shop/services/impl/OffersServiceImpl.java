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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        return offersRepository.findAllByApprovedIsTrue();
    }

    @Transactional
    @Override
    public List<OfferEntity> findAllNotApprovedOffers() {
        return offersRepository.findAllByApprovedIsFalse();
    }

    @Override
    public void addOffer(OfferAddBindingModel offerAddBindingModel, String username) throws IOException {
        List<CloudinaryImage> cloudinaryImages = new ArrayList<>();
        List<MultipartFile> images = offerAddBindingModel.getPictures();
        images.forEach(i -> {
            try {
                CloudinaryImage cloudinaryImage = cloudinaryService.upload(i);
                cloudinaryImages.add(cloudinaryImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        CloudinaryImage cloudinaryImage = cloudinaryService.upload(offerAddBindingModel.getPicture());
        OfferEntity offer = modelMapper.map(offerAddBindingModel, OfferEntity.class);
        offer.setCategory(CategoryEnum.valueOf(offerAddBindingModel.getCategory()));
        offer.setGender(GenderEnum.valueOf(offerAddBindingModel.getGender()));
        offer.setSize(SizeEnum.valueOf(offerAddBindingModel.getSize()));
        offer.setOwner(userService.findByUsername(username));
        List<String> imagesUrls = cloudinaryImages.stream().map(CloudinaryImage::getUrl).collect(Collectors.toList());
        offer.setImagesUrl(imagesUrls);
//        offer.setImagesUrl(List.of(cloudinaryImage.getUrl()));
        offer.setApproved(false);

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

    @Override
    public void init() {
        if (offersRepository.count() == 0){
            OfferEntity offerEntity = new OfferEntity();
            offerEntity.setName("Теникса на Boss");
            offerEntity.setDescription("Предлагам чисто нова тениска на Boss, не е използвана и е в перфектно състояние!");
            offerEntity.setCategory(CategoryEnum.SHIRT);
            offerEntity.setSize(SizeEnum.M);
            offerEntity.setGender(GenderEnum.MALE);
            offerEntity.setOwner(userService.findById(1));
            offerEntity.setPrice(BigDecimal.valueOf(100));
            offerEntity.setImagesUrl(List.of("https://res.cloudinary.com/ditaldvoc/image/upload/v1637492723/pf1kjoerci4aricvgofg.jpg",
                    "https://res.cloudinary.com/ditaldvoc/image/upload/v1638713283/hbeu50387414_001_350_cfvhyo.jpg"));
            offersRepository.save(offerEntity);

            OfferEntity offerEntity2 = new OfferEntity();
            offerEntity2.setName("Яке на The North Face");
            offerEntity2.setDescription("Предлагам яке на The North Face, много добре топли и е в перфектно състояние!");
            offerEntity2.setCategory(CategoryEnum.JACKET);
            offerEntity2.setSize(SizeEnum.L);
            offerEntity2.setGender(GenderEnum.MALE);
            offerEntity2.setOwner(userService.findById(1));
            offerEntity2.setPrice(BigDecimal.valueOf(250));
            offerEntity2.setImagesUrl(List.of("https://res.cloudinary.com/ditaldvoc/image/upload/v1638713416/the-north-face-pukheno-iake-retro-nuptse-nf0a4timjk31-cheren-regular-fit_u8fwlx.jpg",
                    "https://res.cloudinary.com/ditaldvoc/image/upload/v1638713424/341852.002_3_jsvs7m.jpg"));
            offersRepository.save(offerEntity2);
        }

    }
}
