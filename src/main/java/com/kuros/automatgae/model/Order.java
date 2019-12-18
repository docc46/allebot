package com.kuros.automatgae.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String email;
    private String login;
    private String boughtAt;
    private BigInteger offerId;
    private String price;
    private boolean msgSent;

    public Order(String email, String login, String boughtAt, BigInteger offerId, String price) {
        this.email = email;
        this.login = login;
        this.boughtAt = boughtAt;
        this.offerId = offerId;
        this.price = price;
        this.msgSent = false;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public BigInteger getOfferId() {
        return offerId;
    }

    public void setOfferId(BigInteger offerId) {
        this.offerId = offerId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isMsgSent() {
        return msgSent;
    }

    public void setMsgSent(boolean msgSent) {
        this.msgSent = msgSent;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", boughtAt='" + boughtAt + '\'' +
                ", offerId=" + offerId +
                ", price='" + price + '\'' +
                ", msgSent=" + msgSent +
                '}';
    }

    public String getBoughtAtFormatted(){
        String formatted = boughtAt.replace('T',' ').substring(0,19);
        return formatted;
    }
}
