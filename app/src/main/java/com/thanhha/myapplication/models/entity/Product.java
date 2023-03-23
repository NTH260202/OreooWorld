package com.thanhha.myapplication.models.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "product")
public class Product implements Serializable {

    @PrimaryKey()
    @SerializedName("id")
    @NonNull
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("image_thumbnail_path")
    private String thumbnail;
    @SerializedName("price")
    private long price;
    @SerializedName("quantity")
    private int quantity;
    private float rating;

    public Product(){}

    public Product(String id, String name, String description, long price, int quantity, float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
    }
    public String getRatingText() {
        return String.valueOf(rating);
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getTextPrice() {
        return String.valueOf(price);
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTextQuantity() {
        return String.valueOf(quantity);
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
