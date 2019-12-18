package com.kuros.automatgae.connection;

import com.kuros.automatgae.model.AuthResponse;
import com.kuros.automatgae.model.messages.MsgTemplate;
import com.kuros.automatgae.model.orderEvents.OrderEventResponse;
import com.kuros.automatgae.model.userOffers.OfferListingDto;
import com.kuros.automatgae.model.userOffers.UserOffersResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Connector {

    private static Connector single_instance = null;

    private Connector() {
    }

    public static Connector getInstance() {
        if (single_instance == null) single_instance = new Connector();
        return single_instance;
    }

    private String lastSeenId;
    private String token;
    private String refreshToken;

    private String getBase64Creds() {
        String plainCreds = "47b3b071788e48cd9b9070a26e1a8db9:KhTY6p251kwpCLhdMIxtZKWYQT8to2hiNfiP7kglIZvLk7ZyYOZZac1Hogewgavp";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }

    public void authorizeAndGetToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Basic " + getBase64Creds());

        HttpEntity<String> request = new HttpEntity<>(headers);
        String uri = "https://allegro.pl.allegrosandbox.pl/auth/oauth/token?grant_type=authorization_code&code=" + code + "&redirect_uri=http://localhost:8080/auth";
        ResponseEntity<AuthResponse> response = restTemplate.exchange(uri, HttpMethod.POST, request, AuthResponse.class);
        AuthResponse authResponse = response.getBody();
        setToken(authResponse.getAccess_token());
        setRefreshToken(authResponse.getRefresh_token());
    }

    public void refreshToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + getBase64Creds());
        HttpEntity<String> request = new HttpEntity<>(headers);
        String uri = "https://allegro.pl.allegrosandbox.pl/auth/oauth/token?grant_type=refresh_token&refresh_token=" + refreshToken + "&redirect_uri=http://localhost:8080/auth";
        ResponseEntity<AuthResponse> response = restTemplate.exchange(uri, HttpMethod.POST, request, AuthResponse.class);
        AuthResponse authResponse = response.getBody();
        setToken(authResponse.getAccess_token());
        setRefreshToken(authResponse.getRefresh_token());
    }

    public List<MsgTemplate> getTemplatesDetails(List<MsgTemplate> templates) {
        List<MsgTemplate> detailedTemplates = new ArrayList<>();
        for (MsgTemplate template : templates) {
            UserOffersResponse response = getUsersOffers(template.getOfferId());
            OfferListingDto offer = response.getOffers().get(0);
            template.setOfferTitle(offer.getName());
            template.setImgUrl(offer.getPrimaryImage().getUrl());
            detailedTemplates.add(template);
        }
        return detailedTemplates;
    }

    public UserOffersResponse getUsersOffers() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.allegro.public.v1+json");
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        String uri = "https://api.allegro.pl.allegrosandbox.pl/sale/offers?type=READY_FOR_PROCESSING";
        ResponseEntity<UserOffersResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request, UserOffersResponse.class);
        return response.getBody();
    }

    public UserOffersResponse getUsersOffers(BigInteger id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.allegro.public.v1+json");
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        String uri = "https://api.allegro.pl.allegrosandbox.pl/sale/offers?type=READY_FOR_PROCESSING&offer.id=" + id;
        ResponseEntity<UserOffersResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request, UserOffersResponse.class);
        return response.getBody();
    }

    public OrderEventResponse getOrderEvents() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.allegro.beta.v1+json");
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        String uri = "https://api.allegro.pl.allegrosandbox.pl/order/events?type=BOUGHT";
        if (lastSeenId != null) uri += "&from=" + lastSeenId;
        ResponseEntity<OrderEventResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request, OrderEventResponse.class);
        return response.getBody();
    }

    public Boolean isConnected() {
        return (token != null);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getLastSeenId() {
        return lastSeenId;
    }

    public void setLastSeenId(String lastSeenId) {
        this.lastSeenId = lastSeenId;
    }
}
