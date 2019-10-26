package com.kuros.automatgae.model.userOffers;

import java.util.ArrayList;

public class UserOffersResponse {
    private ArrayList<OfferListingDto> offers = new ArrayList<OfferListingDto>();

    private int count;

    private int totalCount;

    public ArrayList<OfferListingDto> getOffers() {
        return offers;
    }

    public int getCount() {
        return count;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
