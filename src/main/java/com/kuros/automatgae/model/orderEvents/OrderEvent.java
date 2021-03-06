package com.kuros.automatgae.model.orderEvents;

import com.kuros.automatgae.model.Order;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

public class OrderEvent {
    private String id;
    private OrderEventData order;
    private String type;
    private String occurredAt;

    public String getId() {
        return id;
    }

    public OrderEventData getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }

    public String getOccurredAt() {
        return occurredAt;
    }

    public ArrayList<Order> getOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        for(OrderLineItem lineItem : this.getOrder().getLineItems()){
            Order order = new Order(
                    this.getOrder().getBuyer().getEmail(),
                    this.getOrder().getBuyer().getLogin(),
                    lineItem.getBoughtAt(),
                    new BigInteger(lineItem.getOffer().getId()),
                    lineItem.getPrice().getAmount()
            );
            orders.add(order);
        }
        return orders;
    }
}
