package com.kuros.automatgae.model.checkoutForms;

import javax.persistence.Embeddable;


public class CheckoutFormDeliveryPickupPoint {
    private String id;
    private String name;
    private String description;
    private CheckoutFormDeliveryPickupPointAddress address;
}
