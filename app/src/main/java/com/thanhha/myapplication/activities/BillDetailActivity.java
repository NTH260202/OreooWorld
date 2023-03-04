package com.thanhha.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ActivityBillDetailBinding;
import com.thanhha.myapplication.viewmodels.BillDetailViewModel;
import com.thanhha.myapplication.viewmodels.CartViewModel;
import com.thanhha.myapplication.viewmodels.ProductDetailViewModel;

public class BillDetailActivity extends AppCompatActivity {
    private ActivityBillDetailBinding binding;
    private BillDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_detail);
        doInitialization();
    }

    private void doInitialization() {
        viewModel = new ViewModelProvider(this).get(BillDetailViewModel.class);
        getBillDetail();
    }

    private void getBillDetail() {

    }

}