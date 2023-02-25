package com.thanhha.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thanhha.myapplication.models.Product;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    Flowable<List<Product>> getAllProduct();

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAllPopularProduct();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addNewProduct(Product product);

    @Insert
    void insert(Product note);
}
