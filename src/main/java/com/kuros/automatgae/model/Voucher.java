package com.kuros.automatgae.model;

import javax.persistence.*;

@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String transId;
    private String value;
    private String serialNumber;
    private String voucherCode;
    private boolean sent;
    @ManyToOne
    @JoinColumn
    private Order order;
    @OneToOne
    @JoinColumn
    private TpayOrder tpayOrder;

    public Voucher(String transId, String value, String serialNumber, String voucherCode, boolean sent, Order order, TpayOrder tpayOrder) {
        this.transId = transId;
        this.value = value;
        this.serialNumber = serialNumber;
        this.voucherCode = voucherCode;
        this.sent = sent;
        this.order = order;
        this.tpayOrder = tpayOrder;
    }

    public Voucher(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public TpayOrder getTpayOrder() {
        return tpayOrder;
    }

    public void setTpayOrder(TpayOrder tpayOrder) {
        this.tpayOrder = tpayOrder;
    }

    @Override
    public String toString() {
        return value + ": " + voucherCode;
    }
}
