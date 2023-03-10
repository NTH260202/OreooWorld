package com.thanhha.myapplication.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.dao.ProductDao;
import com.thanhha.myapplication.database.SampleAppDatabase;
import com.thanhha.myapplication.models.entity.Product;

import java.security.Policy;
import java.util.List;

public class ProductRepository {
    private ProductDao productDao;

    public ProductRepository(Application application) {
        SampleAppDatabase sampleAppDatabase = SampleAppDatabase.getDatabase(application);
        productDao = sampleAppDatabase.productDao();
    }

    public LiveData<List<Product>> getAllPopularProducts() {
        LiveData<List<Product>> result = productDao.getAllPopularProduct();
        return result;
    }

    public LiveData<Product> getProductById(String id) {
        return productDao.getProductById(id);
    }
}
