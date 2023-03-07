package com.thanhha.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.models.entity.Product;
import com.thanhha.myapplication.repositories.ProductRepository;

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
