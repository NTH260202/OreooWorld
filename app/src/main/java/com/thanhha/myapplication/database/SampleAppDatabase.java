package com.thanhha.myapplication.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.thanhha.myapplication.dao.BillDao;
import com.thanhha.myapplication.dao.CartDao;
import com.thanhha.myapplication.dao.ProductDao;
import com.thanhha.myapplication.models.entity.Bill;
import com.thanhha.myapplication.models.entity.Cart;
import com.thanhha.myapplication.models.entity.Product;

@Database(entities = {Product.class, Cart.class, Bill.class}, version = 1, exportSchema = false)
public abstract class SampleAppDatabase extends RoomDatabase {
    private static SampleAppDatabase database;
    public static synchronized SampleAppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, SampleAppDatabase.class, "sample_app_db")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
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
            productDAO.insert(new Product("Y1", "Milk Cotton", "Cheap", 15000, 5));
            productDAO.insert(new Product("Y2", "Susan Family", "Medium", 30000, 7));
            productDAO.insert(new Product("Y3", "Himalaya", "Expensive", 60000, 8));
            productDAO.insert(new Product("Y4", "YarnArt JEANS CRAZY", "Medium", 34000, 10));
            productDAO.insert(new Product("Y5", "Susan 3", "Medium", 40000, 7));
            productDAO.insert(new Product("Y6", "Susan 8", "Expensive", 60000, 8));
            productDAO.insert(new Product("Y7", "CHENILLE Yarn", "Cheap", 25000, 5));
            productDAO.insert(new Product("Y8", "ETROFIL JEANS", "Medium", 32000, 7));
            productDAO.insert(new Product("Y9", "YARNART FLOWERS", "Expensive", 170000, 8));
            return null;
        }
    }

    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
    public abstract BillDao billDao();
}
