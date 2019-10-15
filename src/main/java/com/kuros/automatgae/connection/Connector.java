package com.kuros.automatgae.connection;

import com.kuros.automatgae.model.AuthResponse;
import com.kuros.automatgae.model.CheckoutFormsResponse;
import com.kuros.automatgae.model.orderEvents.OrderEventResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Connector {

    private static String token;
    private static int expiresin;
    private static String lastSeenId;
    private static boolean senderOn = false;

    public static void authorizeAndGetToken(String code){
        RestTemplate restTemplate = new RestTemplate();

        String plainCreds = "47b3b071788e48cd9b9070a26e1a8db9:KhTY6p251kwpCLhdMIxtZKWYQT8to2hiNfiP7kglIZvLk7ZyYOZZac1Hogewgavp";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Basic " + base64Creds);

        HttpEntity<String> request = new HttpEntity<>(headers);
        String uri = "https://allegro.pl.allegrosandbox.pl/auth/oauth/token?grant_type=authorization_code&code=" + code + "&redirect_uri=http://localhost:8080/";
	    ResponseEntity<AuthResponse> response = restTemplate.exchange(uri, HttpMethod.POST, request, AuthResponse.class);
        AuthResponse authResponse = response.getBody();
        token =  authResponse.getAccess_token();
        expiresin = authResponse.getExpires_in();
    }

    public static OrderEventResponse getOrderEvents(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.allegro.beta.v1+json");
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        System.out.println("last id: " + getLastSeenId());
        String uri = "https://api.allegro.pl.allegrosandbox.pl/order/events?type=READY_FOR_PROCESSING";
        if(getLastSeenId()!=null) uri = "https://api.allegro.pl.allegrosandbox.pl/order/events?type=READY_FOR_PROCESSING&from=" + getLastSeenId();
        System.out.println("uri: " + uri);
        ResponseEntity<OrderEventResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request, OrderEventResponse.class);
        System.out.println(response.toString());
        return response.getBody();
    }

    public static String getToken() {
        return token;
    }

    public static int getExpiresin() {
        return expiresin;
    }

    public static String getLastSeenId() {
        return lastSeenId;
    }

    public static void setLastSeenId(String lastSeenId) {
        Connector.lastSeenId = lastSeenId;
    }

    public static boolean isSenderOn() {
        return senderOn;
    }

    public static void setSenderOn(boolean senderOn) {
        Connector.senderOn = senderOn;
    }
}
