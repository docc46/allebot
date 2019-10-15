package com.kuros.automatgae.model.checkoutForms;

import javax.persistence.Embeddable;


public class CheckoutFormPaymentReference {
    private String id;
    private String type;
    private String provider;
    private String finishedAt;
    private Price paidAmount;

    public String getFinishedAt() {
        return finishedAt;
    }

    public Price getPaidAmount() {
        return paidAmount;
    }
}
