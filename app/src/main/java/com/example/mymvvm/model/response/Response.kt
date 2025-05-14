package com.example.mymvvm.model.response

import com.example.product_kotlin.model.models.Product

sealed class Response {
    data object Loading : Response()
    data class Success(val data: List<Product>) : Response()
    data class Failure(val error: Throwable) : Response()
}