package com.thanhha.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.models.Cart;
import com.thanhha.myapplication.models.Product;
import com.thanhha.myapplication.repositories.CartRepository;
import com.thanhha.myapplication.repositories.ProductRepository;

import java.util.List;

public class ProductDetailViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);

    }
    public LiveData<Product> getDetailProduct(String id) {
        return productRepository.getProductById(id);
    };
}
