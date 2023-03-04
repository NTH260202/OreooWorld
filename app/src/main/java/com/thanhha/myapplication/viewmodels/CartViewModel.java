package com.thanhha.myapplication.viewmodels;

import android.app.Application;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.thanhha.myapplication.models.Cart;
import com.thanhha.myapplication.models.Product;
import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.repositories.CartRepository;
import com.thanhha.myapplication.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private MediatorLiveData<Item> itemMediatorLiveData;
    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
        productRepository = new ProductRepository(application);
    }
    public void insertItem(String userId, String productId, int quantity, long totalPrice) {
        Cart item = new Cart();
        item.setUserId(userId);
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setTotalPrice(totalPrice);
        cartRepository.insert(item);
    }

    public void updateItem(int id, String userId, String productId, int quantity, long totalPrice) {
        Cart item = new Cart();
        item.setId(id);
        item.setUserId(userId);
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setTotalPrice(totalPrice);
        cartRepository.update(item);
    }

    public LiveData<Item> getById(String productId, String accountId) {
        return cartRepository.getItem(productId, accountId);
    }

    public void removeItem(int cartId) {
        cartRepository.removeItem(cartId);
    };

    public LiveData<List<Item>> getAllItemInCart(String accountId) {
        return cartRepository.getAllItem(accountId);
    }
}
