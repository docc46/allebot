package com.kuros.automatgae.model.checkoutForms;

import javax.persistence.Embeddable;
import java.util.ArrayList;


public class CheckoutFormLineItem {
    private String id;
    private OfferReference offer;
    private int quantity;
    private Price originalPrice;
    private Price price;
    private ArrayList<CheckoutFormAdditionalService> selectedAdditionalServices = new ArrayList<>();
    private String boughtAt;

    public Price getPrice() {
        return price;
    }

    public OfferReference getOffer() {
        return offer;
    }

    public String getBoughtAt() {
        return boughtAt;
    }

    public int getQuantity() {
        return quantity;
    }
}
