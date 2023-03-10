package com.thanhha.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.database.adapter.BillListAdapter;
import com.thanhha.myapplication.database.adapter.BillViewAdapter;
import com.thanhha.myapplication.database.adapter.CartViewAdapter;
import com.thanhha.myapplication.databinding.ActivityBillHistoryBinding;
import com.thanhha.myapplication.databinding.ActivityCartBinding;
import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.models.entity.Bill;
import com.thanhha.myapplication.repositories.ProductRepository;
import com.thanhha.myapplication.utils.Constants;
import com.thanhha.myapplication.utils.PreferenceManager;
import com.thanhha.myapplication.viewmodels.BillViewModel;
import com.thanhha.myapplication.viewmodels.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class BillHistoryActivity extends AppCompatActivity {
    private ActivityBillHistoryBinding binding;
    private BillViewModel viewModel;
    private BillListAdapter adapter;
    private PreferenceManager preferenceManager;
    private List<Bill> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_history);
        preferenceManager = new PreferenceManager(getApplicationContext());
        doInitialization();
    }

    private void doInitialization() {
        binding.itemOrderRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this)
                .get(BillViewModel.class);
        adapter = new BillListAdapter(orders);
        binding.itemOrderRecyclerView.setAdapter(adapter);
        String accountId = preferenceManager.getString(Constants.KEY_ACCOUNT_ID);
        getAllOrder(accountId);
    }

    private void getAllOrder(String accountId) {
        orders.clear();
        binding.setIsLoading(true);
        viewModel.getAllBills(accountId).observe(this, orders ->
                {
                    this.orders.clear();
                    binding.setIsLoading(false);
                    Log.d("Orders", "Load All Orders");
                    if (orders == null || orders.isEmpty()) {
                        Toast.makeText(this,"You have no orders", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                    if (orders.size() > 0) {
                        this.orders.addAll(orders);
                        adapter.notifyDataSetChanged();
                    }
                }
        );
    }
}