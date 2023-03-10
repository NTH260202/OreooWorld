package com.thanhha.myapplication.listeners;

import com.thanhha.myapplication.models.dto.Item;

public interface CartListener {
    void onButtonRemovedClick(Item item);
    void checkOnItems(String itemId);
}
