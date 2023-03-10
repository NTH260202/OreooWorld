package com.thanhha.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.thanhha.myapplication.dao.BillDao;
import com.thanhha.myapplication.dao.CartDao;
import com.thanhha.myapplication.database.SampleAppDatabase;
import com.thanhha.myapplication.models.entity.Bill;
import com.thanhha.myapplication.models.entity.Cart;

import java.util.List;

public class BillRepository {
    private BillDao billDao;

    public BillRepository(Application application) {
        SampleAppDatabase sampleAppDatabase = SampleAppDatabase.getDatabase(application);
        billDao = sampleAppDatabase.billDao();
    }

    public void insert(Bill bill) {
        new InsertBillAsyncTask(billDao).execute(bill);
    }

    public LiveData<List<Bill>> getAllBills(String accountId) {
        return billDao.getAll(accountId);
    }

    private static class InsertBillAsyncTask extends AsyncTask<Bill, Void, Void> {
        private BillDao billDao;

        private InsertBillAsyncTask(BillDao billDao) {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Bill... bills) {
            billDao.insert(bills[0]);
            return null;
        }
    }
}
