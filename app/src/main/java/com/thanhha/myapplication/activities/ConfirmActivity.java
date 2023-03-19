package com.thanhha.myapplication.activities;

import static com.thanhha.myapplication.activities.BillDetailActivity.CHANNEL_1_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ActivityConfirmBinding;
import com.thanhha.myapplication.databinding.ConfirmAddressInfoSheetBinding;
import com.thanhha.myapplication.models.dto.BillModel;
import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.models.entity.Bill;
import com.thanhha.myapplication.models.enumerate.OrderStatus;
import com.thanhha.myapplication.services.CustomNotificationChannel;
import com.thanhha.myapplication.viewmodels.BillDetailViewModel;
import com.thanhha.myapplication.viewmodels.BillViewModel;
import com.thanhha.myapplication.viewmodels.ProductDetailViewModel;

import java.util.List;

public class ConfirmActivity extends AppCompatActivity {
    private ActivityConfirmBinding binding;
    private Bill bill = new Bill();
    private BillDetailViewModel viewModel;
    private BillViewModel billViewModel;
    private ProductDetailViewModel productViewModel;
    private NotificationManagerCompat notificationManager;
    private Intent updateIntent = new Intent("CurrentItems");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomNotificationChannel.createNotificationChannels(getApplicationContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm);
        viewModel = new ViewModelProvider(this)
                .get(BillDetailViewModel.class);
        billViewModel = new ViewModelProvider(this).get(BillViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        notificationManager = NotificationManagerCompat.from(ConfirmActivity.this);

        View bottomSheetView = findViewById(R.id.addressConfirmBottomSheet);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(
                bottomSheetView
        );

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        setListeners(bottomSheetView);
    }

    private void setListeners(View bottomSheetView) {
        Log.d("Listener", "Set up");
        bottomSheetView.findViewById(R.id.confirmButton).setOnClickListener(v -> getAddressInfo(bottomSheetView));
        bottomSheetView.findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getAddressInfo(View bottomSheetView) {
        Log.d("Confirm", "Bill Confirm");
        bill.setAddress(((EditText)bottomSheetView.findViewById(R.id.shippingAddress)).getText().toString());
        bill.setReceiver(((EditText)bottomSheetView.findViewById(R.id.receiverName)).getText().toString());
        bill.setPhoneNumber(((EditText)bottomSheetView.findViewById(R.id.shippingAddress)).getText().toString());

        Intent intent = getIntent();
        BillModel billModel = (BillModel)intent.getExtras().getSerializable("billModel");
        long total = intent.getLongExtra("total", -1);

        bill.setTotal(total);

        int selectedPaymentId = ((RadioGroup)bottomSheetView.findViewById(R.id.payment)).getCheckedRadioButtonId();
        RadioButton selectedPayment = bottomSheetView.findViewById(selectedPaymentId);
        bill.setPaymentType(selectedPayment.getText().toString());

        String billCode = viewModel.updateBill(billModel.getItemIds());

        billViewModel.insert(billCode, total, billModel.getAccountId(), OrderStatus.PREPARED.name());
        boolean isUpdated = productViewModel.updateQuantity(billModel.getItems());

        Log.d("Confirm", "Bill Confirm");
        if (isUpdated) {
            Toast.makeText(this, "Your bill is created!", Toast.LENGTH_SHORT).show();
            updateIntent.putExtra("isPurchased", true);
            updateIntent.putExtra("numOfItems", 0);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            sendNotification("Oreoo World", "Your bill is checkout successfully! Please tracking your order");
        } else {
            Toast.makeText(this, "Product is not enough!", Toast.LENGTH_SHORT).show();
        }
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