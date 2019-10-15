package com.kuros.automatgae.model.orderEvents;

import com.kuros.automatgae.model.checkoutForms.OfferReference;
import com.kuros.automatgae.model.checkoutForms.Price;

public class OrderLineItem {
    private OfferReference offer;
    private Price price;
    private String id;
    private int quantity;
    private Price originalPrice;
    private String boughtAt;

    public OfferReference getOffer() {
        return offer;
    }

    public Price getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Price getOriginalPrice() {
        return originalPrice;
    }

    public String getBoughtAt() {
        return boughtAt;
    }
}
