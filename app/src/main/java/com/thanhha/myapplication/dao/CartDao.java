package com.thanhha.myapplication.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thanhha.myapplication.models.entity.Cart;
import com.thanhha.myapplication.models.dto.Item;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart WHERE cart.userId=:userId")
    LiveData<List<Cart>> getAllProductInCart(String userId);

    @Query("SELECT cart.id, product.name as productName, cart.userId, cart.totalPrice, cart.quantity, cart.userId , cart.isPaid " +
            "FROM cart, product " +
            "WHERE cart.productId = product.id and cart.userId=:userId and isPaid = 0")
    LiveData<List<Item>> getAllItemInCart(String userId);

    @Query("SELECT * FROM cart")
    LiveData<List<Cart>> getAllProductInCart();

    @Insert(onConflict = REPLACE)
    void insert(Cart cart);

    @Query("DELETE FROM cart WHERE cart.id = :id")
    void removeItem(int id);

    @Query("UPDATE cart SET isPaid = '1', billCode = :billCode WHERE id in (:itemIds)")
    void updateBill(List<Integer> itemIds, String billCode);

    @Query("SELECT cart.id, product.name as productName, cart.userId, cart.totalPrice, cart.quantity, cart.userId " +
            "FROM cart, product " +
            "WHERE cart.productId = product.id and cart.productId=:id")
    LiveData<Item> getById(int id);

    @Query("SELECT cart.id, product.name as productName, cart.userId, cart.totalPrice, cart.quantity, cart.userId " +
            "FROM cart, product " +
            "WHERE cart.productId = product.id and cart.productId=:productId and cart.userId=:accountId")
    LiveData<Item> getById(String productId, String accountId);

    @Query("SELECT cart.id, product.name as productName, cart.userId, cart.totalPrice, cart.quantity, cart.userId, cart.isPaid " +
            "FROM cart, product " +
            "WHERE cart.productId = product.id and cart.productId=:productId and cart.userId=:accountId and isPaid = '0'")
    Item getRawItemById(String productId, String accountId);

    @Update(onConflict = REPLACE)
    void update(Cart cart);
}
