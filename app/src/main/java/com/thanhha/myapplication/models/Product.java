package com.thanhha.myapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    private String is;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
}
