package com.thanhha.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.adapter.ProductViewAdapter;
import com.thanhha.myapplication.databinding.ActivityMainBinding;
import com.thanhha.myapplication.models.Product;
import com.thanhha.myapplication.viewmodels.MostPopularProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private int currentPage;
    private List<Product> products = new ArrayList<>();
    private ProductViewAdapter adapter;
    private MostPopularProductViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();
    }

    private void getMostPopularProduct() {
        activityMainBinding.setIsLoading(true);
        viewModel.getPopularProduct().observe(this, products ->
        {
            activityMainBinding.setIsLoading(true);
            if (products == null) {
                System.out.println("No products");
            }
            if (products != null) {
                this.products.addAll(products);
                adapter.notifyDataSetChanged();
            }
        }
        );
    }

    private void doInitialization() {
        System.out.println("Main Activity: " + this);
        activityMainBinding.productRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this)
                .get(MostPopularProductViewModel.class);
        adapter = new ProductViewAdapter(products);
        activityMainBinding.productRecyclerView.setAdapter(adapter);
        getMostPopularProduct();
    }

    private void getMostPopularTVShows() {

    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        } else {
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        };
    }

//    @Override
//    public void onProductClicked(Product product) {
//        Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
//        intent.putExtra("product", product);
//        startActivity(intent);
//    }
}