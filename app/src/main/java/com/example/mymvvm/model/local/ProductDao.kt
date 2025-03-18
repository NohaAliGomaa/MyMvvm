package com.example.product_kotlin.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.product_kotlin.model.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(product: Product)

    @Query("SELECT * FROM products")
     fun getFavoriteProducts(): Flow<List<Product>>

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun removeFromFavorites(productId: Int):Int
}
