package com.example.clothes_shop.services.cloudinary;

import com.example.clothes_shop.models.entities.BaseEntity;
import com.example.clothes_shop.models.entities.OfferEntity;

import javax.persistence.*;

@Entity
public class CloudinaryImage extends BaseEntity {

    public String url;
    public String publicId;
    @ManyToOne
    public OfferEntity offer;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public void setOffer(OfferEntity offer) {
        this.offer = offer;
    }
}
