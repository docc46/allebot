package com.kuros.automatgae.model;

import javax.persistence.*;

@Entity
@Table(name = "tpayorders")
public class TpayOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String tr_id;
    private String tr_date;
    private String tr_amount;
    private String tr_paid;
    private String tr_error;
    private String tr_email;
    @OneToOne(mappedBy = "tpayOrder", cascade = CascadeType.ALL)
    private Voucher voucher;

    public TpayOrder(String tr_id, String tr_date, String tr_amount, String tr_paid, String tr_error, String tr_email, Voucher voucher) {
        this.tr_id = tr_id;
        this.tr_date = tr_date;
        this.tr_amount = tr_amount;
        this.tr_paid = tr_paid;
        this.tr_error = tr_error;
        this.tr_email = tr_email;
        this.voucher = voucher;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public String getTr_id() {
        return tr_id;
    }

    public void setTr_id(String tr_id) {
        this.tr_id = tr_id;
    }

    public String getTr_date() {
        return tr_date;
    }

    public void setTr_date(String tr_date) {
        this.tr_date = tr_date;
    }

    public String getTr_amount() {
        return tr_amount;
    }

    public void setTr_amount(String tr_amount) {
        this.tr_amount = tr_amount;
    }

    public String getTr_paid() {
        return tr_paid;
    }

    public void setTr_paid(String tr_paid) {
        this.tr_paid = tr_paid;
    }

    public String getTr_error() {
        return tr_error;
    }

    public void setTr_error(String tr_error) {
        this.tr_error = tr_error;
    }

    public String getTr_email() {
        return tr_email;
    }

    public void setTr_email(String tr_email) {
        this.tr_email = tr_email;
    }

}
