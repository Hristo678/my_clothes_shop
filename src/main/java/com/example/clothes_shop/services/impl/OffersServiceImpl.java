package com.example.clothes_shop.services.impl;

import com.example.clothes_shop.models.bindingModels.OfferAddBindingModel;
import com.example.clothes_shop.models.bindingModels.OfferUpdateBindingModel;
import com.example.clothes_shop.models.entities.OfferEntity;
import com.example.clothes_shop.models.entities.UserEntity;
import com.example.clothes_shop.models.enums.*;
import com.example.clothes_shop.repositories.OffersRepository;
import com.example.clothes_shop.repositories.UserRepository;
import com.example.clothes_shop.services.OffersService;
import com.example.clothes_shop.services.UserService;
import com.example.clothes_shop.services.cloudinary.CloudinaryImage;
import com.example.clothes_shop.services.cloudinary.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private  static boolean isOwner = false;

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
    public Page<OfferEntity> findAllOffersWithCriteria(String gender, String clotheCondition,
                                                       String category, double minPrice, double maxPrice, int page, int size) {

        if (clotheCondition.equals("ALL") && category.equals("ALL")){
            return offersRepository.findAllByApprovedIsTrueAndGenderEqualsAndPriceBetween(
                    GenderEnum.valueOf(gender),
                    BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice), PageRequest.of(page, size));
        }else if (category.equals("ALL")){
            return offersRepository.findAllByApprovedIsTrueAndGenderEqualsAndClotheConditionEqualsAndPriceBetween(
                    GenderEnum.valueOf(gender), ConditionEnum.valueOf(clotheCondition),
                     BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice), PageRequest.of(page, size));
        }else if (clotheCondition.equals("ALL")){
            return offersRepository.findAllByApprovedIsTrueAndGenderEqualsAndCategoryEqualsAndPriceBetween(
                    GenderEnum.valueOf(gender),
                    CategoryEnum.valueOf(category), BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice), PageRequest.of(page, size));
        }else
        return offersRepository.findAllByApprovedIsTrueAndGenderEqualsAndClotheConditionEqualsAndCategoryEqualsAndPriceBetween(
                GenderEnum.valueOf(gender), ConditionEnum.valueOf(clotheCondition),
                CategoryEnum.valueOf(category), BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice), PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public List<OfferEntity> findAllNotApprovedOffers() {
        return offersRepository.findAllByApprovedIsFalse();
    }

    @Override
    @Transactional
    public void addOffer(OfferAddBindingModel offerAddBindingModel, String username) throws IOException {

        OfferEntity offer = modelMapper.map(offerAddBindingModel, OfferEntity.class);
        offer.setCategory(CategoryEnum.valueOf(offerAddBindingModel.getCategory()));
        offer.setGender(GenderEnum.valueOf(offerAddBindingModel.getGender()));
        offer.setOwner(userService.findByUsername(username));
        offer.setApproved(false);
        offersRepository.save(offer);

        List<CloudinaryImage> cloudinaryImages = new ArrayList<>();
        List<MultipartFile> images = offerAddBindingModel.getPictures();
        images.forEach(i -> {
            try {
                CloudinaryImage cloudinaryImage = cloudinaryService.upload(i);
                cloudinaryImage.setOffer(offer);
                cloudinaryService.saveImage(cloudinaryImage);
                cloudinaryImages.add(cloudinaryImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        offer.setImagesUrl(cloudinaryImages);
//        CloudinaryImage cloudinaryImage = cloudinaryService.upload(offerAddBindingModel.getPicture());


//        offerAddBindingModel.getSizes().forEach(s -> {
//            offer.addSize(SizeEnum.valueOf(s));
//        });
//        offer.setSize(SizeEnum.valueOf(offerAddBindingModel.getSize()));

//        List<String> imagesUrls = cloudinaryImages.stream().map(CloudinaryImage::getUrl).collect(Collectors.toList());

//        offer.setImagesUrl(List.of(cloudinaryImage.getUrl()));



    }

    @Override
    @Transactional
    public List<OfferEntity> findTheNewestOffers() {
        long idForTheLastSixOffers = offersRepository.count() - 6 > 0 ? offersRepository.count() -1 : 0;

        return offersRepository.findAllByIdIsGreaterThanAndApprovedIsTrue(idForTheLastSixOffers);
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

        user.getRoles().forEach(r ->{
            if (r.getRole().toString().equals("ADMIN")){
                isOwner = true;
            }
        });
        if (offer.getOwner().getUsername().equals(user.getUsername())) {
            isOwner = true;
        }

        return isOwner;
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
        offerEntity.setSizes(offer.getSizes());
        offerEntity.setCategory(offer.getCategory());
        offerEntity.setDescription(offer.getDescription());
        offerEntity.setName(offer.getName());
        offerEntity.setClotheCondition(offer.getClotheCondition());
        offersRepository.save(offerEntity);
    }

    @Transactional
    @Override
    public void addPictures(Long id, List<MultipartFile> pictures) throws IOException {

        OfferEntity offerEntity = offersRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        pictures.forEach(p -> {
            try {
                CloudinaryImage cloudinaryImage = cloudinaryService.upload(p);
                cloudinaryImage.setOffer(offerEntity);
                cloudinaryService.saveImage(cloudinaryImage);
                offerEntity.getImagesUrl().add(cloudinaryImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void saveOffer(OfferEntity offerEntity){
        offersRepository.save(offerEntity);
    }

    @Override
    public void init() {
        if (offersRepository.count() == 0){
            CloudinaryImage cloudinaryImage1 = new CloudinaryImage();
            cloudinaryImage1.setPublicId("boss_22_s80xkd");
            cloudinaryImage1.setUrl("https://res.cloudinary.com/ditaldvoc/image/upload/v1646848238/boss_22_s80xkd.jpg");
            CloudinaryImage cloudinaryImage2 = new CloudinaryImage();
            cloudinaryImage2.setPublicId("boss_oikd6x");
            cloudinaryImage2.setUrl("https://res.cloudinary.com/ditaldvoc/image/upload/v1646844485/boss_oikd6x.jpg");

            cloudinaryService.saveImage(cloudinaryImage1);
            cloudinaryService.saveImage(cloudinaryImage2);

            OfferEntity offerEntity = new OfferEntity();
            offerEntity.setName("Теникса на Boss");
            offerEntity.setDescription("Предлагам чисто нова тениска на Boss, не е използвана и е в перфектно състояние!");
            offerEntity.setCategory(CategoryEnum.SHIRT);
            offerEntity.setSizes(List.of(SizeEnum.M));
            offerEntity.setClotheCondition(ConditionEnum.NEW);
            offerEntity.setGender(GenderEnum.MALE);
            offerEntity.setOwner(userService.findById(1));
            offerEntity.setPrice(BigDecimal.valueOf(100));
            offerEntity.setImagesUrl(List.of(cloudinaryImage1,
                    cloudinaryImage2));
            offersRepository.save(offerEntity);


        }

    }

    @Override
    public Page<OfferEntity> findAllByPage(int page, int size) {
        return offersRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<OfferEntity> findAllWithKeyword(GenderEnum gender, String name, String description, int page, int size) {
        return offersRepository.findAllByApprovedIsTrueAndGenderEqualsAndNameOrDescriptionContains
                (gender,name, description, PageRequest.of(page, size));
    }

    @Override
    public Page<OfferEntity> findAllWithGender(GenderEnum gender, int page, int size) {
        return offersRepository.findAllByApprovedIsTrueAndGenderEquals
                (gender,PageRequest.of(page, size));
    }
}
