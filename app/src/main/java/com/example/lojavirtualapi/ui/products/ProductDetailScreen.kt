package com.example.lojavirtualapi.ui.products

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Product
import com.example.lojavirtualapi.ui.posts.PostDetailScreen


@Composable
fun ProductDetailScreen(id: Int, nav: NavController) {

    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(id) {
        product = RetrofitInstance.api.getProduct(id)
    }

    product?.let { p ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            AsyncImage(
                model = p.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(280.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = p.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Preço: R$ ${p.price}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = p.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
