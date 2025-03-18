package com.example.product_kotlin.model.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String,
    var isFavourite :Boolean =false
) {
}