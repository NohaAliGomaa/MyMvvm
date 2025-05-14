package com.example.mymvvm.favourite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.mymvvm.allproduct.ProductItem
import com.example.mymvvm.favourite.ui.theme.MyMvvmTheme
import com.example.mymvvm.model.local.LocalDataSourceImpl
import com.example.mymvvm.model.remote.RemotDataSourceImpl
import com.example.mymvvm.model.response.Response
import com.example.product_kotlin.model.local.AppDatabase
import com.example.product_kotlin.model.models.Product
import com.example.product_kotlin.model.repo.ProductRepository
import com.example.product_kotlin.viewmodel.ProductFactory
import com.example.product_kotlin.viewmodel.ProductViewModel

class FavoritesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val factory= ProductFactory(ProductRepository(
                LocalDataSourceImpl(AppDatabase.getInstance(this).productDao()),
                RemotDataSourceImpl()
            ))
            val viewModel= ViewModelProvider(this,factory).get(ProductViewModel::class)
            FavoriteScreen(viewModel)
        }
    }
}

@Composable
fun FavoriteScreen(viewModel: ProductViewModel) {
    val productsState = viewModel.favProducts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getFavoriteProducts()
    }

    Column {
        Text(
            "Favorite Products",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )

        when (val products = productsState.value) {
            is Response.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            is Response.Success -> {
                val productList = products.data
                LazyColumn {
                    items(productList) { product ->
                        FavoriteProductItem(product, onRemoveFavorite = {
                            viewModel.removeFromFavorites(product.id)
                        })
                    }
                }
            }
            is Response.Failure -> {
                val errorMessage = products.error.message ?: "Unknown Error"
                Text(
                    "Error: $errorMessage",
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FavoriteProductItem(product: Product, onRemoveFavorite: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(product.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )

            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(product.title, style = MaterialTheme.typography.headlineSmall)
                Text("Price: $${product.price}", style = MaterialTheme.typography.bodySmall)
            }

            Button(
                onClick = { onRemoveFavorite() },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Remove")
            }
        }
    }
}
