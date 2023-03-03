package com.thanhha.myapplication.database.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ItemContainerSliderImageBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{

    private String[] sliderImage;
    private LayoutInflater layoutInflater;
    public ImageSliderAdapter(String[] sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImageBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_slider_image, parent, false
        );
        return new ImageSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindingSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerSliderImageBinding binding;

        public ImageSliderViewHolder(@NonNull ItemContainerSliderImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindingSliderImage(String imageUrl) {
            binding.setImageUrl(imageUrl);
        }
    }
}
