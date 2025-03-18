package com.example.mymvvm.model.remote

import android.util.Log
import com.example.product_kotlin.model.models.Product
import com.example.product_kotlin.model.remote.ApiService
import com.example.product_kotlin.model.remote.RetrofitHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow


class  RemotDataSourceImpl: RemoteDataSource {
    private val productsApi: ApiService = RetrofitHelper.getRetrofit()
        .create(ApiService::class.java)
    override suspend fun getProducts(): Flow<List<Product>> = flow {
            val response = productsApi.getProducts()
            Log.i("TAG", "Fetched ${response.body()?.products?.size} products from API")
            if (response.isSuccessful) {
                Log.i("TAG", "Successed ${response.body()?.products?.size} products from API")
                response.body()?.products?.let { emit(it) }
            } else {
                Log.e("TAG", "API Error: ${response.errorBody()?.string()}")
                emptyList<Product>() // Return empty list on API failure
             }
    }

}
