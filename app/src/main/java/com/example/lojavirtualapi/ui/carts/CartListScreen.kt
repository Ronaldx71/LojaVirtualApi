package com.example.lojavirtualapi.ui.carts

import androidx.compose.foundation.clickable
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

@Composable
fun CartListScreen(nav: NavController) {

    var list by remember { mutableStateOf<List<Cart>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val response = RetrofitInstance.api.getCarts()
        list = response.carts
        loading = false
    }

    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        items(list) { cart ->

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { nav.navigate("cart/${cart.id}") }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Carrinho ID: ${cart.id}", style = MaterialTheme.typography.titleMedium)
                    Text("Usuário: ${cart.userId}")
                    Text("Total: R$ ${cart.total}")
                }
            }
        }
    }
}
