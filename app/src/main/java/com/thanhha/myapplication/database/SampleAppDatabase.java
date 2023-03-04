package com.thanhha.myapplication.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.thanhha.myapplication.dao.CartDao;
import com.thanhha.myapplication.dao.ProductDao;
import com.thanhha.myapplication.models.Cart;
import com.thanhha.myapplication.models.Product;

@Database(entities = {Product.class, Cart.class}, version = 1, exportSchema = false)
public abstract class SampleAppDatabase extends RoomDatabase {
    private static SampleAppDatabase database;
    public static synchronized SampleAppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, SampleAppDatabase.class, "oreoo_world_db")
                    .addCallback(roomCallback)
                    .build();
        }
        return database;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(database).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao productDAO;
        private CartDao cartDAO;

        private PopulateDbAsyncTask(SampleAppDatabase db) {
            productDAO = db.productDao();
            cartDAO = db.cartDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDAO.insert(new Product("Y1", "Milk Cotton", "Cheap", 15000));
            productDAO.insert(new Product("Y2", "Susan Family", "Medium", 30000));
            productDAO.insert(new Product("Y3", "Himalaya", "Expensive", 60000));
            cartDAO.insert(new Cart("zszBhUTjGqIC1Im6d13j", "Y1", 2, 30000));
            return null;
        }
    }

    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
}
