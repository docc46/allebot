package com.kuros.automatgae.model.orderEvents;

import java.util.ArrayList;

public class OrderEventData {
    private BuyerReference buyer;
    private ArrayList<OrderLineItem> lineItems = new ArrayList<>();
    private SellerReference seller;
    private CheckoutFormReference checkoutForm;

    public BuyerReference getBuyer() {
        return buyer;
    }

    public ArrayList<OrderLineItem> getLineItems() {
        return lineItems;
    }

    public SellerReference getSeller() {
        return seller;
    }

    public CheckoutFormReference getCheckoutForm() {
        return checkoutForm;
    }
}
