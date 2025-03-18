package com.example.product_kotlin.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.product_kotlin.model.local.AppDatabase
import com.example.product_kotlin.model.models.Product
import com.example.product_kotlin.model.repo.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductViewModel(val repo:ProductRepository) :ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _favProducts = MutableLiveData<List<Product>>()
    val favProducts: LiveData<List<Product>> = _favProducts

    private val mutableMessage: MutableLiveData<String> = MutableLiveData("")
    val message: LiveData<String> = mutableMessage


    init{
        getProducts()
    }
    fun getProducts() {
        viewModelScope.launch {
            try {
                repo.fetchProducts()
                    .collect { productsList ->
                        Log.i("TAG", "Products loaded: ${products.value}")
                        _products.postValue(productsList) // Update LiveData safely

                    }
            } catch (e: Exception) {
                mutableMessage.postValue("An Error Occurred: ${e.message}")
            }
        }
    }

    fun addToFavorites(product: Product) {
        viewModelScope.launch (Dispatchers.IO) {
            try{
                val result = repo.addToFavorites(product)
            }catch (e:Exception){
                mutableMessage.postValue("An Error Occured :${e.message}")
            }
        }

    }

    fun removeFromFavorites(productId: Int) {
        viewModelScope.launch {
            repo.removeFromFavorites(productId)
            getFavoriteProducts()
        }
    }

    fun getFavoriteProducts(): LiveData<List<Product>> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getFavoriteProducts().collect { list ->
                    _favProducts.postValue(list) // Post collected list to LiveData
                    Log.i("TAG", "Updated favorites: ${favProducts.value}")
                }
            } catch (e: Exception) {
                mutableMessage.postValue("An Error Occurred: ${e.message}")
            }
        }
        return favProducts
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