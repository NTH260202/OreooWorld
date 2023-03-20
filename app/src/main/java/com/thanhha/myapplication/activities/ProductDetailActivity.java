package com.thanhha.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ActivityProductDetailactivityBinding;
import com.thanhha.myapplication.models.entity.Product;
import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.repositories.CartRepository;
import com.thanhha.myapplication.utils.Constants;
import com.thanhha.myapplication.utils.PreferenceManager;
import com.thanhha.myapplication.viewmodels.CartViewModel;
import com.thanhha.myapplication.viewmodels.ProductDetailViewModel;

import java.io.File;
import java.io.IOException;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailactivityBinding binding;
    private ProductDetailViewModel viewModel;
    private CartViewModel cartViewModel;
    private PreferenceManager preferenceManager;
    public int totalQuantity = 1;
    private CartRepository cartRepository = new CartRepository(getApplication());

    public Product productDetail;

    private StorageReference firebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detailactivity);
        preferenceManager = new PreferenceManager(getApplicationContext());
        firebaseStorage = FirebaseStorage.getInstance().getReference("yarn_type.jpg");
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
    private void loadPlantImage() {

    }

    private void getProductDetail() {
        binding.setIsLoading(true);
        Intent intent = getIntent();
        String productId = String.valueOf(intent.getStringExtra("productId"));


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

                        try {
                            File localFile = File.createTempFile("tempFile", ".jpg");
                            firebaseStorage.getFile(localFile).addOnSuccessListener(listener -> {
                                Bitmap productImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                binding.imageProduct.setImageBitmap(productImage);
                                binding.imageProduct.setVisibility(View.VISIBLE);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
        );
    }

    private void saveItemInCart() {
        String accountId = preferenceManager.getString(Constants.KEY_ACCOUNT_ID);

        Item item = cartRepository.getRawItem(productDetail.getId(), accountId);
        if (totalQuantity > productDetail.getQuantity()) {
            Toast.makeText(getApplicationContext(), "Product is not enough!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (item == null) {
            cartRepository.insert(accountId, productDetail.getId(), totalQuantity, totalQuantity * productDetail.getPrice());
        } else {
            int updatedQuantity = (totalQuantity + Integer.parseInt(item.getQuantity()));
            long updatedPrice = updatedQuantity * productDetail.getPrice();
            cartRepository.update(item.getId(), accountId, productDetail.getId(), updatedQuantity, updatedPrice);
        }
        Toast.makeText(getApplicationContext(), "Item is saved into your  cart!", Toast.LENGTH_SHORT).show();
    }
}