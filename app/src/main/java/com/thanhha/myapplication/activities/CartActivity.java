package com.thanhha.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.database.adapter.CartViewAdapter;
import com.thanhha.myapplication.databinding.ActivityCartBinding;
import com.thanhha.myapplication.listeners.CartListener;
import com.thanhha.myapplication.models.dto.Item;
import com.thanhha.myapplication.repositories.ProductRepository;
import com.thanhha.myapplication.utils.Constants;
import com.thanhha.myapplication.utils.PreferenceManager;
import com.thanhha.myapplication.viewmodels.CartViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListener {
    private ActivityCartBinding binding;
    private CartViewModel viewModel;
    private CartViewAdapter adapter;
    private PreferenceManager preferenceManager;
    private ProductRepository productRepository;
    private List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        preferenceManager = new PreferenceManager(getApplicationContext());
        productRepository = new ProductRepository(getApplication());
        doInitialization();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d("Items", "Delete item");
                viewModel.removeItem(adapter.getItemAt(viewHolder.getAdapterPosition()).getId());
                Toast.makeText(getApplicationContext(), "Item is removed from your  cart!", Toast.LENGTH_SHORT).show();
                items.clear();
            }
        }).attachToRecyclerView(binding.itemCartRecyclerView);
    }

    private void doInitialization() {
        binding.itemCartRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this)
                .get(CartViewModel.class);
        adapter = new CartViewAdapter(items, this);
        binding.itemCartRecyclerView.setAdapter(adapter);
        String accountId = preferenceManager.getString(Constants.KEY_ACCOUNT_ID);
        getAllItems(accountId);

    }

    private void getAllItems(String accountId) {
        items.clear();
        binding.setIsLoading(true);
        viewModel.getAllItemInCart(accountId).observe(this, items ->
                {
                    binding.setIsLoading(false);
                    if (items == null || items.isEmpty()) {
                        Toast.makeText(this,"No items in your cart", Toast.LENGTH_SHORT).show();
                    }
                    if (items.size() > 0) {
                        Log.d("Item", "Load All Items");
                        this.items.addAll(items);
                        adapter.notifyDataSetChanged();
                    }
                }
        );
    }

    private void setListeners() {
        binding.buyButton.setOnClickListener(v -> createBill(items));
    }

    private void createBill(List<Item> items) {
        Intent intent = new Intent(getApplicationContext(), BillDetailActivity.class);
        intent.putExtra("itemList", (Serializable) items);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Bill is created!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonRemovedClick(Item item) {

    }
}