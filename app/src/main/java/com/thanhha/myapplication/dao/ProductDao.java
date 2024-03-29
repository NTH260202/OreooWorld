package com.thanhha.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thanhha.myapplication.models.entity.Product;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    Flowable<List<Product>> getAllProduct();

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAllPopularProduct();

    @Query("SELECT * FROM product WHERE id=:id")
    LiveData<Product> getProductById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addNewProduct(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product note);

    @Query("UPDATE product SET quantity = :quantity WHERE id =:id")
    void updateQuantity(String id, int quantity);
}
