package com.kuros.automatgae.model;

public class TpayResponse {

    private int id;
    private String tr_id;
    private String tr_date;
    private String tr_crc;
    private String tr_amount;
    private String tr_paid;
    private String tr_desc;
    private String tr_status;
    private String tr_error;
    private String tr_email;
    private String md5sum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTr_crc() {
        return tr_crc;
    }

    public void setTr_crc(String tr_crc) {
        this.tr_crc = tr_crc;
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

    public String getTr_desc() {
        return tr_desc;
    }

    public void setTr_desc(String tr_desc) {
        this.tr_desc = tr_desc;
    }

    public String getTr_status() {
        return tr_status;
    }

    public void setTr_status(String tr_status) {
        this.tr_status = tr_status;
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

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    @Override
    public String toString() {
        return "TpayResponse{" +
                "id=" + id +
                ", tr_id='" + tr_id + '\'' +
                ", tr_date='" + tr_date + '\'' +
                ", tr_crc='" + tr_crc + '\'' +
                ", tr_amount='" + tr_amount + '\'' +
                ", tr_paid='" + tr_paid + '\'' +
                ", tr_desc='" + tr_desc + '\'' +
                ", tr_status='" + tr_status + '\'' +
                ", tr_error='" + tr_error + '\'' +
                ", tr_email='" + tr_email + '\'' +
                ", md5sum='" + md5sum + '\'' +
                '}';
    }
}
