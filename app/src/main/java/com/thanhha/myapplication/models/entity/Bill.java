package com.thanhha.myapplication.models.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(tableName = "bill")
public class Bill implements Serializable {
    @PrimaryKey
    private String billCode;
    private long total;
    private LocalDateTime createdAt;

    public Bill(String billCode, long total, LocalDateTime createdAt) {
        this.billCode = billCode;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Bill() {
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
}
