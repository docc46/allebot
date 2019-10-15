package com.kuros.automatgae.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String stringId;
    private String email;
    private String login;
    private String boughtAt;
    private String offerId;
    private String price;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<Voucher> vouchers;
    private int quantity;

    public Order(String stringId, String email, String login, String boughtAt, String offerId, String price, Set<Voucher> vouchers, int quantity) {
        this.stringId = stringId;
        this.email = email;
        this.login = login;
        this.boughtAt = boughtAt;
        this.offerId = offerId;
        this.price = price;
        this.vouchers = vouchers;
        this.quantity = quantity;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(String boughtAt) {
        this.boughtAt = boughtAt;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", stringId='" + stringId + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", boughtAt='" + boughtAt + '\'' +
                ", offerId='" + offerId + '\'' +
                ", price='" + price + '\'' +
                ", vouchers=" + vouchers +
                ", quantity=" + quantity +
                '}';
    }
}
