package com.kuros.automatgae.model.userOffers;

public class OfferListingDto {

    private String id;
    private String name;
//    private OfferCategory category;
    private OfferListingDtoImage primaryImage;
//    private SellingMode sellingMode;
//    private OfferListingDtoV1SaleInfo saleInfo;
//    private OfferListingDtoV1Stock stock;
//    private OfferListingDtoV1Stats stats;
//    private OfferListingDtoV1Publication publication;
//    private AfterSalesServices afterSalesServices;
//    private OfferAdditionalServices additionalServices;
//    private ExternalId external;
//    private OfferListingDtoV1Delivery delivery;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public OfferListingDtoImage getPrimaryImage() {
        return primaryImage;
    }
}
