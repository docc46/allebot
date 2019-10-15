package com.kuros.automatgae.model.checkoutForms;

import javax.persistence.Embeddable;


public class CheckoutFormInvoiceAddress {
    private String street;
    private String city;
    private String zipCode;
    private String countryCode;
    private CheckoutFormInvoiceAddressCompany company;
    private CheckoutFormInvoiceAddressNaturalPerson naturalPerson;
}
