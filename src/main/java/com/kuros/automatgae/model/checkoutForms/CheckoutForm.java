package com.kuros.automatgae.model.checkoutForms;

import com.kuros.automatgae.model.Order;

import java.math.BigInteger;
import java.util.ArrayList;

public class CheckoutForm {

    private String id;
    private CheckoutFormBuyerReference buyer;
    private String status;
    private CheckoutFormInvoiceInfo invoice;
    private ArrayList<CheckoutFormLineItem> lineItems = new ArrayList<>();
    private ArrayList<CheckoutFormPaymentReference> surcharges = new ArrayList<>();
    private ArrayList<CheckoutFormDiscount> discounts = new ArrayList<>();
    private CheckoutFormSummary summary;
    private String messageToSeller;
    private CheckoutFormPaymentReference payment;
    private CheckoutFormDeliveryReference delivery;

    public ArrayList<CheckoutFormLineItem> getLineItems() {
        return lineItems;
    }


    public String getId() {
        return id;
    }

    public CheckoutFormBuyerReference getBuyer() {
        return buyer;
    }

    public CheckoutFormPaymentReference getPayment() {
        return payment;
    }

    public ArrayList<Order> getOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        for(CheckoutFormLineItem lineItem : this.lineItems){
                Order order = new Order(
                        this.buyer.getEmail(),
                        this.buyer.getLogin(),
                        lineItem.getBoughtAt(),
                        new BigInteger(lineItem.getOffer().getId()),
                        lineItem.getPrice().getAmount()
                        );
                orders.add(order);
        }
        return orders;
    }
}
