package com.kuros.automatgae.model.checkoutForms;

import javax.persistence.Embeddable;


public class OfferReference {
    private String id;
    private String name;
    private JustId external;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
