package com.thanhha.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.thanhha.myapplication.models.Cart;
import com.thanhha.myapplication.repositories.CartRepository;

public class CartViewModel extends AndroidViewModel {
    private CartRepository cartRepository;
    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);

    }
    public void insertItem(String userId, String productId, int quantity, long totalPrice) {
        Cart item = new Cart();
        item.setUserId(userId);
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setTotalPrice(totalPrice);
        cartRepository.insert(item);
    }
}
