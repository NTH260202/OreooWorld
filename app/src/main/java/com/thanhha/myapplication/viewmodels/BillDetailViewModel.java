package com.thanhha.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.thanhha.myapplication.repositories.CartRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class BillDetailViewModel extends AndroidViewModel {
    private CartRepository cartRepository;
    public BillDetailViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
    }

    public void updateBill(List<Integer> itemsId) {
        cartRepository.updateBill(itemsId);
    }
}
