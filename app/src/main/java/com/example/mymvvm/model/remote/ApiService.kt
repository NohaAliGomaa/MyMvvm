package com.example.product_kotlin.model.remote
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<ProductsResponse>
}
