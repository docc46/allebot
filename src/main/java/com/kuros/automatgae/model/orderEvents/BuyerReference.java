package com.kuros.automatgae.model.orderEvents;

public class BuyerReference {
    private String email;
    private String login;
    private boolean guest;
    private String id;

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public boolean isGuest() {
        return guest;
    }

    public String getId() {
        return id;
    }
}
