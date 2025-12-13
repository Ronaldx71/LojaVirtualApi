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
import kotlinx.coroutines.launch

@Composable
fun CartListScreen(nav: NavController) {

    var carts by remember { mutableStateOf<List<Cart>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitInstance.api.getCarts()
                carts = response.carts
            } catch (e: Exception) {
                error = "Erro ao carregar carrinhos"
            } finally {
                loading = false
            }
        }
    }

    when {
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = error ?: "Erro inesperado")
            }
        }

        carts.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhum carrinho encontrado")
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(carts) { cart ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                nav.navigate("cart/${cart.id}")
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Carrinho ID: ${cart.id}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Usuário: ${cart.userId}")
                            Text("Total: R$ ${cart.total}")
                        }
                    }
                }
            }
        }
    }
}
