package com.example.lojavirtualapi.ui.carts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Cart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetailScreen(
    id: Int,
    onBackClick: () -> Unit
) {
    var cart by remember { mutableStateOf<Cart?>(null) }

    LaunchedEffect(id) {
        cart = RetrofitInstance.api.getCart(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Detalhe do Carrinho")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->

        cart?.let { c ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Carrinho de Compras",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(24.dp))

                Column(Modifier.fillMaxWidth()) {
                    Text("Carrinho ID: ${c.id}", style = MaterialTheme.typography.titleLarge)
                    Text("Usuário: ${c.userId}")
                    Text("Total: R$ ${c.total}")
                    Text("Itens: ${c.totalProducts}")

                    Spacer(Modifier.height(16.dp))

                    LazyColumn {
                        items(c.products) { p ->
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
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
