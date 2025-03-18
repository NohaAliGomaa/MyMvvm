package com.example.mymvvm.model.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.product_kotlin.model.models.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertAll(products: List<Product>)

    suspend fun addToFavorites(product: Product)


    suspend fun getFavoriteProducts(): Flow<List<Product>>


    suspend fun removeFromFavorites(productId: Int):Int
}