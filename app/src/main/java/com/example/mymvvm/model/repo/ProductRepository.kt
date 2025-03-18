package com.example.product_kotlin.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mymvvm.model.local.LocalDataSourceImpl
import com.example.mymvvm.model.remote.RemotDataSourceImpl
import com.example.product_kotlin.model.local.AppDatabase
import com.example.product_kotlin.model.models.Product
import com.example.product_kotlin.model.remote.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList

class ProductRepository(private val db: LocalDataSourceImpl,private val remoteDataSource :RemotDataSourceImpl) {

    suspend fun fetchProducts(): Flow<List<Product>> {
        val products =remoteDataSource.getProducts()
        Log.i("TAG", "fetchProducts: ${products}")
        return remoteDataSource.getProducts()


    }


    suspend fun addToFavorites(product: Product) {
       db.addToFavorites(product)
    }

    suspend fun getFavoriteProducts(): Flow<List<Product>> {
        return db.getFavoriteProducts()
    }

    suspend fun removeFromFavorites(productId: Int) {
        db.removeFromFavorites(productId)
    }
}
