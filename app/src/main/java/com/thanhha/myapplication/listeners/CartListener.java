package com.thanhha.myapplication.listeners;

import com.thanhha.myapplication.models.Product;
import com.thanhha.myapplication.models.dto.Item;

public interface CartListener {
    void onButtonRemovedClick(Item item);
}
