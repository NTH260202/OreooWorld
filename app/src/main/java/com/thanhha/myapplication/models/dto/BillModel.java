package com.thanhha.myapplication.models.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BillModel implements Serializable {
    private static final long serialVersionUID = 2L;

    private String billCode;
    private long total;
    private LocalDateTime createdAt;
    private String receiver;
    private String address;
    private String phoneNumber;
    private List<Item> items;
    private List<Integer> itemIds;
    private String paymentType;
    private String accountId;

    public BillModel(String billCode, long total, LocalDateTime createdAt) {
        this.billCode = billCode;
        this.total = total;
        this.createdAt = createdAt;
    }

    public BillModel() {
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
