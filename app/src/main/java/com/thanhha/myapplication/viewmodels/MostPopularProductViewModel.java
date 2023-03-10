package com.thanhha.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.models.entity.Product;
import com.thanhha.myapplication.repositories.ProductRepository;

import java.util.List;

public class MostPopularProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    public MostPopularProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }
    public LiveData<List<Product>> getPopularProduct() {
        return productRepository.getAllPopularProducts();
    };
}
