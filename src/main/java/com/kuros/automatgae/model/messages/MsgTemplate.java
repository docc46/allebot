package com.kuros.automatgae.model.messages;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "templates")
public class MsgTemplate {

    @Id
    @Column(columnDefinition = "bigint(20)")
    private BigInteger offerId;
    private String text;
    private String offerTitle;
    private String imgUrl;

    public MsgTemplate() {
    }

    public BigInteger getOfferId() {
        return offerId;
    }

    public void setOfferId(BigInteger offerId) {
        this.offerId = offerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
