package com.thanhha.myapplication.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.dao.ProductDao;
import com.thanhha.myapplication.database.SampleAppDatabase;
import com.thanhha.myapplication.models.Product;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> popularProducts;

    public ProductRepository(Application application) {
        SampleAppDatabase sampleAppDatabase = SampleAppDatabase.getDatabase(application);
        productDao = sampleAppDatabase.productDao();
        popularProducts = productDao.getAllPopularProduct();
    }

    public LiveData<List<Product>> getAllPopularProducts() {
        return productDao.getAllPopularProduct();
    }
}
