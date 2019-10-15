package com.kuros.automatgae.model.checkoutForms;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;


public class CheckoutFormDeliveryReference {
    private CheckoutFormDeliveryAddress address;
    private CheckoutFormDeliveryMethod method;
    private CheckoutFormDeliveryPickupPoint pickupPoint;
    private Price cost;
    private boolean smart;
}
