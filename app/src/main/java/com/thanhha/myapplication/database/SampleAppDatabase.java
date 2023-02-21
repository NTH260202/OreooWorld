package com.thanhha.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thanhha.myapplication.dao.ProductDao;
import com.thanhha.myapplication.models.Product;

@Database(entities = Product.class, version = 1, exportSchema = false)
public abstract class SampleAppDatabase extends RoomDatabase {
    private static SampleAppDatabase database;
    public static synchronized SampleAppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, SampleAppDatabase.class, "oreoo_world_db").build();
        }
        return database;
    }

    public abstract ProductDao productDao();
}
