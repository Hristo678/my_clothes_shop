package com.example.clothes_shop.models.viewModels;

public class SellerViewModel {

    private String username;
    private Integer numberOfOffers;
    private long offerId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumberOfOffers() {
        return numberOfOffers;
    }

    public void setNumberOfOffers(Integer numberOfOffers) {
        this.numberOfOffers = numberOfOffers;
    }

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }
}
