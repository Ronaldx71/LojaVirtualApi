package com.example.lojavirtualapi.ui.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Product
import com.example.lojavirtualapi.ui.posts.PostListScreen


@Composable
fun ProductListScreen(nav: NavController) {

    var list by remember { mutableStateOf<List<Product>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    // ✅ searchText precisa ser estado e NÃO pode ser null
    var searchText by remember { mutableStateOf("") }

    // ✅ LaunchedEffect deve observar searchText
    LaunchedEffect(searchText) {
        loading = true

        val response = if (searchText.isBlank()) {
            RetrofitInstance.api.getProducts()
        } else {
            RetrofitInstance.api.searchProducts(searchText)
        }

        list = response.products
        loading = false
    }





    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(list) { item ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { nav.navigate("product/${item.id}") }
            ) {
                Row(modifier = Modifier.padding(16.dp)) {

                    // Imagem do produto
                    AsyncImage(
                        model = item.thumbnail,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Info do produto
                    Column {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "R$ ${item.price}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
