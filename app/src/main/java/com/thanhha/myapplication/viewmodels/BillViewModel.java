package com.thanhha.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.models.entity.Bill;
import com.thanhha.myapplication.repositories.BillRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BillViewModel extends AndroidViewModel {
    private BillRepository billRepository;
    public BillViewModel(@NonNull Application application) {
        super(application);
        billRepository = new BillRepository(application);
    }

    public LiveData<List<Bill>> getAllBills(String accountId) {
        return billRepository.getAllBills(accountId);
    }

    public void insert(String billCode, long total, String accountId, String status) {
        Bill bill = new Bill();
        bill.setAccountId(accountId);
        bill.setStatus(status);
        bill.setBillCode(billCode);
        bill.setTotal(total);
        bill.setCreatedAt(LocalDateTime.now());
        billRepository.insert(bill);
    }
}
