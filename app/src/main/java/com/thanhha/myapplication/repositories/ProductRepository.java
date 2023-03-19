package com.thanhha.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.dao.CartDao;
import com.thanhha.myapplication.dao.ProductDao;
import com.thanhha.myapplication.database.SampleAppDatabase;
import com.thanhha.myapplication.models.entity.Cart;
import com.thanhha.myapplication.models.entity.Product;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.Policy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

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

    public void updateQuantities(Map<String, Integer> productQuantities) {
        new UpdateQuantitiesAsyncTask(productDao).execute(productQuantities);
    }

    private static class UpdateQuantitiesAsyncTask extends AsyncTask<Map<String, Integer>, Void, Void> {
        private ProductDao productDao;

        private UpdateQuantitiesAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Map<String, Integer>... maps) {
            for (Map.Entry<String, Integer> entry : maps[0].entrySet()) {
                productDao.updateQuantity(entry.getKey(), entry.getValue());
            }

            return null;
        }
    }
}
