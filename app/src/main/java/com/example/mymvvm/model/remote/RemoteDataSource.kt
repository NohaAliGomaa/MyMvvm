package com.example.mymvvm.model.remote

import com.example.product_kotlin.model.models.Product
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
   suspend fun getProducts(): Flow<List<Product>>
}