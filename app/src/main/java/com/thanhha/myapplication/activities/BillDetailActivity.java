package com.thanhha.myapplication.activities;

import static com.thanhha.myapplication.services.CustomNotificationChannel.CHANNEL_1_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.database.adapter.BillViewAdapter;
import com.thanhha.myapplication.database.adapter.CartViewAdapter;
import com.thanhha.myapplication.databinding.ActivityBillDetailBinding;
import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.models.entity.Bill;
import com.thanhha.myapplication.models.enumerate.OrderStatus;
import com.thanhha.myapplication.repositories.BillRepository;
import com.thanhha.myapplication.utils.Constants;
import com.thanhha.myapplication.utils.PreferenceManager;
import com.thanhha.myapplication.viewmodels.BillDetailViewModel;
import com.thanhha.myapplication.viewmodels.BillViewModel;
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
    private BillRepository billRepository = new BillRepository(getApplication());
    private BillViewModel billViewModel;
    private NotificationManagerCompat notificationManager;

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

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
        notificationManager = NotificationManagerCompat.from(BillDetailActivity.this);
        binding.itemCartRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this)
                .get(BillDetailViewModel.class);
        billViewModel = new ViewModelProvider(this).get(BillViewModel.class);
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
        long total = 0;
        for (Item item : items) {
            itemIds.add(item.getId());
            total = Long.parseLong(item.getTotalPrice()) + total;
        }
        String billCode = viewModel.updateBill(itemIds);

        billViewModel.insert(billCode, total, accountId, OrderStatus.PREPARED.name());

        Toast.makeText(this, "Your bill is created!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent("CurrentItems");
        intent.putExtra("numOfItems", 0);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        sendNotification("Oreoo World", "Your bill is created");
        finish();

    }

    private void sendNotification(String title, String message) {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);
    }

}