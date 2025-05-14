package com.example.product_kotlin.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mymvvm.model.response.Response
import com.example.product_kotlin.model.local.AppDatabase
import com.example.product_kotlin.model.models.Product
import com.example.product_kotlin.model.repo.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductViewModel(val repo:ProductRepository) :ViewModel() {

    private val _products = MutableStateFlow<Response>(Response.Loading)
    val products: StateFlow<Response> = _products.asStateFlow()

    private val _favProducts = MutableStateFlow<Response>(Response.Loading)
    val favProducts: StateFlow<Response> = _favProducts.asStateFlow()

    private val mutableMessage = MutableStateFlow("")
    val message: StateFlow<String> = mutableMessage.asStateFlow()


    init{
        getProducts()
    }
    fun getProducts() {
        viewModelScope.launch {
                repo.fetchProducts()
                    .catch { e -> _products.value = Response.Failure(e) }
                    .collect { productsList ->
                        Log.i("TAG", "Products loaded: ${products.value}")
                        _products.value = Response.Success(productsList)

                    }

        }
    }

    fun addToFavorites(product: Product) {
        viewModelScope.launch (Dispatchers.IO) {
            try{
                val result = repo.addToFavorites(product)
            }catch (e:Exception){
                mutableMessage.value = "An Error Occured :${e.message}"
            }
        }

    }

    fun removeFromFavorites(productId: Int) {
        viewModelScope.launch {
            repo.removeFromFavorites(productId)
            getFavoriteProducts()
        }
    }

    fun getFavoriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
                repo.getFavoriteProducts()
                    .catch { e -> _favProducts.value = Response.Failure(e) }
                    .collect { list ->
                    _favProducts.value = Response.Success(list)// Post collected list to LiveData
                    Log.i("TAG", "Updated favorites: ${favProducts.value}")
                }

        }
    }

}
class ProductFactory(val repo:ProductRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            ProductViewModel(repo) as T
        }
        else{
            throw IllegalArgumentException("View Model Calss not found")
        }
    }
}