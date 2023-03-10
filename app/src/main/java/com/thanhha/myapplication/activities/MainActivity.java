package com.thanhha.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.database.adapter.ProductViewAdapter;
import com.thanhha.myapplication.databinding.ActivityMainBinding;
import com.thanhha.myapplication.listeners.ProductListener;
import com.thanhha.myapplication.models.entity.Product;
import com.thanhha.myapplication.utils.Constants;
import com.thanhha.myapplication.utils.PreferenceManager;
import com.thanhha.myapplication.viewmodels.MostPopularProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListener {
    private ActivityMainBinding activityMainBinding;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private List<Product> products = new ArrayList<>();
    private ProductViewAdapter adapter;
    private MostPopularProductViewModel viewModel;
    private PreferenceManager preferenceManager;

    private String accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());
        doInitialization();
    }

    private void getMostPopularProduct() {
        activityMainBinding.setIsLoading(true);
        viewModel.getPopularProduct().observe(this, products ->
        {
            activityMainBinding.setIsLoading(false);
            if (products == null || products.isEmpty()) {
                System.out.println("No products");
            } else {
                Log.d("Product", "Load All Products");
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
        adapter = new ProductViewAdapter(products, this);
        activityMainBinding.productRecyclerView.setAdapter(adapter);
        accountName = preferenceManager.getString(Constants.KEY_NAME);
        setUpDrawerLayout();
        getMostPopularProduct();
    }

    private void setUpDrawerLayout() {
        setSupportActionBar(activityMainBinding.appBar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityMainBinding.mainDrawer, R.string.app_name, R.string.app_name);
        View headerView = activityMainBinding.navView.getHeaderView(0);
        TextView accountName = headerView.findViewById(R.id.nav_header_name);
        accountName.setText(this.accountName);
        activityMainBinding.navView.setNavigationItemSelectedListener(item -> {
            Log.d("Selected Activity", "" + item.getItemId());
            switch (item.getItemId()) {
                case R.id.menu_bill: {
                    startActivity(new Intent(getApplicationContext(), BillHistoryActivity.class));
                    break;
                }
                case R.id.menu_cart: {
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    break;
                }
                case R.id.menu_location: {
                    startActivity(new Intent(getApplicationContext(), MapsMarkerActivity.class));
                    break;
                }
            }
            return true;
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

//    private void toggleLoading() {
//        if (currentPage == 1) {
//            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
//                activityMainBinding.setIsLoading(false);
//            } else {
//                activityMainBinding.setIsLoading(true);
//            }
//        } else {
//            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
//                activityMainBinding.setIsLoading(false);
//            } else {
//                activityMainBinding.setIsLoading(true);
//            }
//        };
//    }

    @Override
    public void onProductClicked(Product product) {
        Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
        intent.putExtra("productId", product.getId());
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = (String) item.getTitle();
//        actionBarDrawerToggle.getToolbarNavigationClickListener().onClick(new ViewModelProvider(this,));
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}