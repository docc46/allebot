package com.kuros.automatgae.model.checkoutForms;

import com.kuros.automatgae.model.Order;
import com.kuros.automatgae.model.Voucher;

import java.util.ArrayList;
import java.util.HashSet;

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
                        this.id,
                        this.buyer.getEmail(),
                        this.buyer.getLogin(),
                        lineItem.getBoughtAt(),
                        lineItem.getOffer().getId(),
                        lineItem.getPrice().getAmount(),
                        new HashSet<>(),
                        lineItem.getQuantity()
                        );
                orders.add(order);
        }
        return orders;
    }
}
