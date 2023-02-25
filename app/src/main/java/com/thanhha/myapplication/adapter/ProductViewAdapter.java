package com.thanhha.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ItemContainerProductBinding;
import com.thanhha.myapplication.models.Product;

import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ProductViewHolder>{
    private List<Product> products;
    private LayoutInflater layoutInflater;

    public ProductViewAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerProductBinding productBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_container_product,
                parent,
                false);
        return new ProductViewHolder(productBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindProduct(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerProductBinding binding;

        public ProductViewHolder(@NonNull ItemContainerProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindProduct(Product product) {
            binding.setProduct(product);
            binding.executePendingBindings();
        }
    }
}
