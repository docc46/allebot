package com.kuros.automatgae.model;

import com.kuros.automatgae.model.checkoutForms.CheckoutForm;

import java.util.ArrayList;

public class CheckoutFormsResponse {

    private int count;
    private int totalCount;
    private ArrayList<CheckoutForm> checkoutForms = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public ArrayList<CheckoutForm> getCheckoutForms() {
        return checkoutForms;
    }



}
