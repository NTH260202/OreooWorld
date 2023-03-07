package com.thanhha.myapplication.database.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ItemContainerItemCartBinding;
import com.thanhha.myapplication.listeners.CartListener;
import com.thanhha.myapplication.models.dto.Item;

import java.util.List;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder>{
    private Context context;
    private List<Item> items;
    private LayoutInflater layoutInflater;
    private CartListener listener;

    public CartViewAdapter(Context context, List<Item> items, CartListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerItemCartBinding cartBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_container_item_cart,
                parent,
                false);
        return new CartViewHolder(cartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bindingCart(items.get(position));
        Intent intent = new Intent("CurrentItems");
        intent.putExtra("numOfItems", getItemCount());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public Item getItemAt(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerItemCartBinding binding;

        public CartViewHolder(@NonNull ItemContainerItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindingCart(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
//            binding.itemRemove.setOnClickListener(view -> listener.onButtonRemovedClick(item));
        }
    }
}
