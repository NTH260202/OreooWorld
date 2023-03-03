package com.thanhha.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.dao.CartDao;
import com.thanhha.myapplication.database.SampleAppDatabase;
import com.thanhha.myapplication.models.Cart;
import com.thanhha.myapplication.models.Product;

import java.util.List;

public class CartRepository {
    private CartDao cartDao;
    public CartRepository(Application application) {
        SampleAppDatabase sampleAppDatabase = SampleAppDatabase.getDatabase(application);
        cartDao = sampleAppDatabase.cartDao();
    }

    public void insert(Cart item) {
        new InsertCartAsyncTask(cartDao).execute(item);
    }

    private static class InsertCartAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDao cartDao;

        private InsertCartAsyncTask(CartDao noteDao) {
            this.cartDao = noteDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.insert(carts[0]);
            return null;
        }
    }
}
