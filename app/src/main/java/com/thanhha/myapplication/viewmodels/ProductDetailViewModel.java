package com.thanhha.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.models.entity.Product;
import com.thanhha.myapplication.repositories.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);

    }
    public LiveData<Product> getDetailProduct(String id) {
        return productRepository.getProductById(id);
    };

    public boolean updateQuantity(List<Item> items) {
        Map<String, Integer> quantity = new HashMap<>();
        for (Item item : items) {
            if (Integer.parseInt(item.getInStock()) - Integer.parseInt(item.getQuantity()) < 0) {
                return false;
            }
            quantity.put(item.getProductId(), Integer.parseInt(item.getInStock()) - Integer.parseInt(item.getQuantity()));
        }
        productRepository.updateQuantities(quantity);
        return true;
    }
}
