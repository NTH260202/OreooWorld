package com.thanhha.myapplication.models.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BillModel implements Serializable {
    private static final long serialVersionUID = 2L;

    private String billCode;
    private long total;
    private LocalDateTime createdAt;

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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
