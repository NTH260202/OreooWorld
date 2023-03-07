package com.thanhha.myapplication.database.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ItemContainerBillBinding;
import com.thanhha.myapplication.models.dto.Item;

import java.util.List;

public class BillViewAdapter extends RecyclerView.Adapter<BillViewAdapter.BillViewHolder>{
    private List<Item> items;
    private LayoutInflater layoutInflater;

    public BillViewAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BillViewAdapter.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerBillBinding billBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_container_bill,
                parent,
                false);
        return new BillViewAdapter.BillViewHolder(billBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewAdapter.BillViewHolder holder, int position) {
        holder.bindingCart(items.get(position));
    }

    public Item getItemAt(int position) {
        return items.get(position);
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    class BillViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerBillBinding binding;

        public BillViewHolder(@NonNull ItemContainerBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindingCart(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
