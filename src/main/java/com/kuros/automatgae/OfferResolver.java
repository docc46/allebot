package com.kuros.automatgae;

public class OfferResolver {

    public static String resolveIdToVoucherValue(String offerId){
        switch (offerId) {
            case "6206233575": return "100.00";
            case "6206234477": return "50.00";
        }
        return "no offer found";
    }

}
