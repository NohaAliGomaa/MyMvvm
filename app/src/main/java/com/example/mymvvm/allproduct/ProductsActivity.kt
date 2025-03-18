package com.example.mymvvm.allproduct

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mymvvm.allproduct.ui.theme.MyMvvmTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.mymvvm.model.local.LocalDataSourceImpl
import com.example.mymvvm.model.remote.RemotDataSourceImpl
import com.example.product_kotlin.model.local.AppDatabase
import com.example.product_kotlin.model.models.Product
import com.example.product_kotlin.model.repo.ProductRepository
import com.example.product_kotlin.viewmodel.ProductFactory
import com.example.product_kotlin.viewmodel.ProductViewModel

class ProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val factory= ProductFactory(
                ProductRepository(
                LocalDataSourceImpl(AppDatabase.getInstance(this).productDao()),
                RemotDataSourceImpl()
            )
            )
            val viewModel= ViewModelProvider(this,factory).get(ProductViewModel::class)
            ProductScreen(viewModel)
        }
    }
}
@Composable
fun ProductScreen(viewModel: ProductViewModel) {
    val products by viewModel.products.observeAsState(emptyList())
//    LaunchedEffect(Unit) {
//        viewModel.getProducts()
//        Log.i("TAG", "ProductScreen: $products")
//    }
    Column {
        Text("All Products", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(8.dp))
        LazyColumn {
            items(products) { product ->
                ProductItem(product, onAddFavorite = { viewModel.addToFavorites(product) })
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onAddFavorite: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onAddFavorite() }
    ) {
        Image(
            painter = rememberImagePainter(product.thumbnail),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(product.title, style = MaterialTheme.typography.headlineMedium)
            Text("Price: $${product.price}", style = MaterialTheme.typography.bodySmall)
        }
    }
}