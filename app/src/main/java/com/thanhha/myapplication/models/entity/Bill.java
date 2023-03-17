package com.thanhha.myapplication.models.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(tableName = "bill")
public class Bill implements Serializable {
    @PrimaryKey
    @NonNull
    private String billCode;
    private long total;
    @TypeConverters({DateTimeConverter.class})
    private LocalDateTime createdAt;
    private String accountId;
    private String status;
    private String receiver;
    private String address;
    private String phoneNumber;
    private String paymentType;

    public Bill(String billCode, long total, LocalDateTime createdAt, String accountId, String status) {
        this.billCode = billCode;
        this.total = total;
        this.createdAt = createdAt;
        this.accountId = accountId;
        this.status = status;
    }

    public Bill() {
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getCreatedAtText() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public String getTotalText() {
        return String.valueOf(total);
    }
}
