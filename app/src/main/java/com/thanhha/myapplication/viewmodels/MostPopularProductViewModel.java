package com.thanhha.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.thanhha.myapplication.models.Product;
import com.thanhha.myapplication.repositories.ProductRepository;

import java.util.List;

import lombok.AllArgsConstructor;

public class MostPopularProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    private LiveData<List<Product>> allProducts;
    public MostPopularProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        allProducts = productRepository.getAllPopularProducts();
    }
    public LiveData<List<Product>> getPopularProduct() {
        return productRepository.getAllPopularProducts();
    };
}
