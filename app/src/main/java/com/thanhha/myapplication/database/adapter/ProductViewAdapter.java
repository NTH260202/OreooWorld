package com.thanhha.myapplication.database.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ItemContainerProductBinding;
import com.thanhha.myapplication.listeners.ProductListener;
import com.thanhha.myapplication.models.entity.Product;

import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ProductViewHolder>{
    private List<Product> products;
    private LayoutInflater layoutInflater;
    private ProductListener productListener;
    private Bitmap productImage;

    public ProductViewAdapter(List<Product> products,
                              ProductListener listener, Bitmap productImage) {
        this.products = products;
        this.productListener = listener;
        this.productImage = productImage;
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

    public void setProductImage(Bitmap productImage) {
        this.productImage = productImage;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindProduct(products.get(position), productImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerProductBinding binding;

        public ProductViewHolder(@NonNull ItemContainerProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindProduct(Product product, Bitmap productImage) {
            binding.setProduct(product);
            binding.imagePlant.setImageBitmap(productImage);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(view -> productListener.onProductClicked(product));
        }
    }
}
