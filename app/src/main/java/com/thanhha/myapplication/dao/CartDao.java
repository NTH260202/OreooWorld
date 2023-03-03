package com.thanhha.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thanhha.myapplication.models.Cart;
import com.thanhha.myapplication.models.Product;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart WHERE userId=:userId")
    LiveData<List<Cart>> getAllProductInCart(String userId);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);
}
