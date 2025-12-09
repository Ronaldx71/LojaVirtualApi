package com.example.lojavirtualapi.ui.carts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Cart
import com.example.lojavirtualapi.model.CartProduct

@Composable
fun CartDetailScreen(id: Int, nav: NavController) {

    var cart by remember { mutableStateOf<Cart?>(null) }

    LaunchedEffect(id) {
        cart = RetrofitInstance.api.getCart(id)
    }

    cart?.let { c ->

        Column(Modifier.padding(16.dp)) {
            Text("Carrinho ID: ${c.id}", style = MaterialTheme.typography.titleLarge)
            Text("Usuário: ${c.userId}")
            Text("Total: R$ ${c.total}")
            Text("Itens: ${c.totalProducts}")

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(c.products) { p: CartProduct ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(p.title, style = MaterialTheme.typography.titleMedium)
                            Text("Preço: R$ ${p.price}")
                            Text("Quantidade: ${p.quantity}")
                        }
                    }
                }
            }
        }
    } ?: Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
