package com.thanhha.myapplication.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thanhha.myapplication.models.entity.Bill;

import java.util.List;

@Dao
public interface BillDao {
    @Insert(onConflict = REPLACE)
    void insert(Bill bill);

    @Query("SELECT * FROM bill WHERE bill.accountId = :accountId")
    LiveData<List<Bill>> getAll(String accountId);
}
