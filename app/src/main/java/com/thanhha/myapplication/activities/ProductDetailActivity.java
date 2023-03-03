package com.thanhha.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ActivityProductDetailactivityBinding;
import com.thanhha.myapplication.models.Product;
import com.thanhha.myapplication.utils.Constants;
import com.thanhha.myapplication.utils.PreferenceManager;
import com.thanhha.myapplication.viewmodels.CartViewModel;
import com.thanhha.myapplication.viewmodels.ProductDetailViewModel;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailactivityBinding binding;
    private ProductDetailViewModel viewModel;
    private CartViewModel cartViewModel;
    private PreferenceManager preferenceManager;
    public int totalQuantity = 1;

    public Product productDetail = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detailactivity);
        preferenceManager = new PreferenceManager(getApplicationContext());
        doInitialization();
        setListeners();
    }

    private void doInitialization() {
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding.imageBack.setOnClickListener(view -> onBackPressed());

        getProductDetail();
    }
    private void setListeners() {
        binding.itemAdd.setOnClickListener(v -> {
            totalQuantity ++;
            binding.quantity.setText(String.valueOf(totalQuantity));
        });

        binding.itemRemove.setOnClickListener(v -> {
            if (totalQuantity > 1) {
                totalQuantity--;
            }
            binding.quantity.setText(String.valueOf(totalQuantity));
        });

        binding.addCartButton.setOnClickListener(v -> saveItemInCart());
    }

    private void getProductDetail() {
        binding.setIsLoading(true);
        Intent intent = getIntent();
        String productId = String.valueOf(getIntent().getStringExtra("productId"));

        viewModel.getDetailProduct(productId).observe(
                this,
                    product -> {
                        productDetail = product;
                        binding.setIsLoading(false);
                        binding.setProduct(product);
                        binding.textName.setText(product.getName());
                        binding.textPrice.setText(String.valueOf(product.getPrice()));
                        binding.textDescription.setText(product.getDescription());
                        binding.textName.setVisibility(View.VISIBLE);
                        binding.textDescription.setVisibility(View.VISIBLE);
                        binding.textPrice.setVisibility(View.VISIBLE);

                    }
        );
    }

    private void saveItemInCart() {
        String accountId = preferenceManager.getString(Constants.KEY_ACCOUNT_ID);
        cartViewModel.insertItem(accountId, productDetail.getId(), totalQuantity, totalQuantity * productDetail.getPrice());
        Toast.makeText(getApplicationContext(), "Item is saved into your  cart!", Toast.LENGTH_SHORT).show();
    }
}