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
import com.thanhha.myapplication.utils.Constants;

@Database(entities = {Product.class, Cart.class, Bill.class}, version = 1, exportSchema = false)
public abstract class SampleAppDatabase extends RoomDatabase {
    private static SampleAppDatabase database;
    public static synchronized SampleAppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, SampleAppDatabase.class, "sample_app_db_v3")
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
            productDAO.insert(new Product("Y1", "Milk Cotton", Constants.DUMP_TEXT, 15000, 5, 4.5f));
            productDAO.insert(new Product("Y2", "Susan Family", Constants.DUMP_TEXT, 30000, 7, 5.0f));
            productDAO.insert(new Product("Y3", "Himalaya", Constants.DUMP_TEXT, 60000, 8, 4.2f));
            productDAO.insert(new Product("Y4", "YarnArt JEANS CRAZY", Constants.DUMP_TEXT, 34000, 10, 4.5f));
            productDAO.insert(new Product("Y5", "Susan 3", Constants.DUMP_TEXT, 40000, 7, 4.0f));
            productDAO.insert(new Product("Y6", "Susan 8", Constants.DUMP_TEXT, 60000, 8, 4.5f));
            productDAO.insert(new Product("Y7", "CHENILLE Yarn", Constants.DUMP_TEXT, 25000, 5, 4.7f));
            productDAO.insert(new Product("Y8", "ETROFIL JEANS", Constants.DUMP_TEXT, 32000, 7, 4.0f));
            productDAO.insert(new Product("Y9", "YARNART FLOWERS", Constants.DUMP_TEXT, 170000, 8, 4.5f));
            return null;
        }
    }

    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
    public abstract BillDao billDao();
}
