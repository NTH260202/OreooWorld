package com.thanhha.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.dao.CartDao;
import com.thanhha.myapplication.database.SampleAppDatabase;
import com.thanhha.myapplication.models.Cart;
import com.thanhha.myapplication.models.Product;
import com.thanhha.myapplication.models.dto.Item;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CartRepository {
    private CartDao cartDao;
    public CartRepository(Application application) {
        SampleAppDatabase sampleAppDatabase = SampleAppDatabase.getDatabase(application);
        cartDao = sampleAppDatabase.cartDao();
    }

    public Item getRawItem(String productId, String accountId) {
//        return cartDao.getRawItemById(productId, accountId);
        Callable<Item> callable = new Callable<Item>() {
            @Override
            public Item call() throws Exception {
                return cartDao.getRawItemById(productId, accountId);
            }
        };

        Future<Item> future = Executors.newSingleThreadExecutor().submit(callable);

        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insert(Cart item) {
        new InsertCartAsyncTask(cartDao).execute(item);
    }

    public void update(Cart item) {
        new UpdateCartAsyncTask(cartDao).execute(item);
    }

    public void update(int id, String accountId, String productId, int totalQuantity, long totalPrice) {
        Cart cart = new Cart();
        cart.setId(id);
        cart.setUserId(accountId);
        cart.setProductId(productId);
        cart.setQuantity(totalQuantity);
        cart.setTotalPrice(totalPrice);
        new UpdateCartAsyncTask(cartDao).execute(cart);
    }

    public void insert(String accountId, String productId, int totalQuantity, long totalPrice) {
        Cart cart = new Cart();
        cart.setUserId(accountId);
        cart.setProductId(productId);
        cart.setQuantity(totalQuantity);
        cart.setTotalPrice(totalPrice);
        new InsertCartAsyncTask(cartDao).execute(cart);
    }
    public LiveData<List<Item>> getAllItem(String accountId) {
        LiveData<List<Item>> result = cartDao.getAllItemInCart(accountId);
        return result;
    }

    public LiveData<Item> getItem(int id) {
        return cartDao.getById(id);
    }

    public LiveData<Item> getItem(String productId, String accountId) {
        return cartDao.getById(productId, accountId);
    }
    public void removeItem(int cartId) {
        new RemoveCartAsyncTask(cartDao).execute(cartId);
    }
    private static class RawQueryCartAsyncTask extends AsyncTask<String, Void, Item> {
        private CartDao cartDao;
        private RawQueryCartAsyncTask(CartDao noteDao) {
            this.cartDao = noteDao;
        }
        @Override
        protected Item doInBackground(String... strings) {
            return cartDao.getRawItemById(strings[0], strings[1]);
        }
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

    private static class RemoveCartAsyncTask extends AsyncTask<Integer, Void, Void> {
        private CartDao cartDao;

        private RemoveCartAsyncTask(CartDao noteDao) {
            this.cartDao = noteDao;
        }

        @Override
        protected Void doInBackground(Integer... cartIds) {
            cartDao.removeItem(cartIds[0]);
            return null;
        }
    }

    private static class UpdateCartAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDao cartDao;

        private UpdateCartAsyncTask(CartDao noteDao) {
            this.cartDao = noteDao;
        }

        @Override
        protected Void doInBackground(Cart... cartIds) {
            cartDao.update(cartIds[0]);
            return null;
        }
    }
}
