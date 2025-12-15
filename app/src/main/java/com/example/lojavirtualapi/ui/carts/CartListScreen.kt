package com.example.lojavirtualapi.ui.carts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Cart
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext


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
                items(carts) { cart -> // <--- ESTE 'cart' É O PARÂMETRO
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                nav.navigate("cart/${cart.id}")
                            }
                    ) {
                        // NOVO CÓDIGO AQUI
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // O ÍCONE (usa o import Icons.Filled.ShoppingCart)
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Carrinho",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp).padding(end = 8.dp)
                            )

                            // O TEXTO (USA A VARIÁVEL 'cart' do LazyColumn)
                            Column {
                                Text(
                                    text = "Carrinho ID: ${cart.id}", // Aqui não deve dar erro
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text("Total: R$ ${cart.total}")
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CartListScreenPreview() {
    // Cria um NavController de 'mentirinha' para a preview
    val fakeNavController = NavController(LocalContext.current)

    // Chama a tela principal
    // ATENÇÃO: Se o projeto usa um tema, você deve envolver a chamada com o tema.
    // Ex: LojaVirtualApiTheme { CartListScreen(nav = fakeNavController) }

    CartListScreen(nav = fakeNavController)
}