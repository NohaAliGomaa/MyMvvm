package com.example.mymvvm.model.repo

import com.example.product_kotlin.model.models.Product

interface ProductsRepositoryInte {
    suspend fun getProducts(isOnline : Boolean): List<Product>?
    suspend fun insertProducts(product: Product)
    suspend fun deleteProduct(product: Product)
}