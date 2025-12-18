package com.example.lojavirtualapi.ui.users

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    id: Int,
    onBackClick: () -> Unit
) {

    var user by remember { mutableStateOf<User?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(id) {
        user = RetrofitInstance.api.getUser(id)
        loading = false
    }

    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val u = user ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhe do Usuário") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            AsyncImage(
                model = u.image,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))

            Text("${u.firstName} ${u.lastName}", style = MaterialTheme.typography.titleLarge)
            Text(u.email)
            Text("Idade: ${u.age}")
            Text("Telefone: ${u.phone}")
            Text("Cidade: ${u.address.city}")
            Text("País: ${u.address.country}")

            Spacer(Modifier.height(24.dp))

            Text("Informações adicionais:", style = MaterialTheme.typography.titleMedium)
            Text("Universidade: ${u.university}")
            Text("Empresa: ${u.company.name}")
            Text("Cargo: ${u.company.title}")
        }
    }
}
