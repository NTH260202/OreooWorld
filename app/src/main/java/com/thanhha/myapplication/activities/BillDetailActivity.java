package com.thanhha.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.database.adapter.BillViewAdapter;
import com.thanhha.myapplication.database.adapter.CartViewAdapter;
import com.thanhha.myapplication.databinding.ActivityBillDetailBinding;
import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.utils.Constants;
import com.thanhha.myapplication.utils.PreferenceManager;
import com.thanhha.myapplication.viewmodels.BillDetailViewModel;
import com.thanhha.myapplication.viewmodels.CartViewModel;
import com.thanhha.myapplication.viewmodels.ProductDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class BillDetailActivity extends AppCompatActivity {
    private ActivityBillDetailBinding binding;
    private BillDetailViewModel viewModel;
    private PreferenceManager preferenceManager;
    private String accountId;
    private BillViewAdapter adapter;
    private List<Item> items;
    private long totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_detail);
        preferenceManager = new PreferenceManager(getApplicationContext());
        accountId = preferenceManager.getString(Constants.KEY_ACCOUNT_ID);
        doInitialization();
        getBillDetail();
        setListeners();
    }
    private void setListeners() {
        binding.paidButton.setOnClickListener(v -> createBill());
    }

    private void doInitialization() {
        binding.itemCartRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this)
                .get(BillDetailViewModel.class);
        adapter = new BillViewAdapter(items);
        binding.itemCartRecyclerView.setAdapter(adapter);
    }

    private void getBillDetail() {
        binding.setIsLoading(true);
        Intent intent = getIntent();
        String test = intent.getStringExtra("test");
        items = (List<Item>)getIntent().getExtras().getSerializable("itemList");

        for (Item item : items) {
            totalPrice = totalPrice + Long.parseLong(item.getTotalPrice());
        }
        binding.totalPrice.setText("Total Price: " + totalPrice);
        adapter.setItems(items);
        binding.setIsLoading(false);
        adapter.notifyDataSetChanged();
    }

    private void createBill() {
        List<Integer> itemIds = new ArrayList<>();
        for (Item item : items) {
            itemIds.add(item.getId());
        }
        viewModel.updateBill(itemIds);
        Toast.makeText(this, "Your order is created!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent("CurrentItems");
        intent.putExtra("numOfItems", 0);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        finish();

    }


}