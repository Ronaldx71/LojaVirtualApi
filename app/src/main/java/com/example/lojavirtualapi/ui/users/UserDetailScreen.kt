package com.example.lojavirtualapi.ui.users

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(id: Int, nav: NavController) {

    var user by remember { mutableStateOf<User?>(null) }
    var loading by remember { mutableStateOf(true) }

    // ---- CHAMADA DA API ----
    LaunchedEffect(id) {
        val response = RetrofitInstance.api.getUser(id)
        user = response
        loading = false
    }

    // ---- LOADING ----
    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // ---- SE DER ERRO ----
    val u = user ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Detalhe do Usuário")
                },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->

        // ---- LAYOUT BÁSICO PARA A EQUIPE COMPLETAR ----
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // Foto do usuário
            AsyncImage(
                model = u.image,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))

            Text(text = "${u.firstName} ${u.lastName}", style = MaterialTheme.typography.titleLarge)
            Text(text = u.email, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Idade: ${u.age}")
            Text(text = "Telefone: ${u.phone}")
            Text(text = "Cidade: ${u.address.city}")
            Text(text = "País: ${u.address.country}")

            Spacer(Modifier.height(24.dp))

            // ---- ÁREA PARA A EQUIPE COMPLETAR ----
            Text(
                text = "Informações adicionais:",
                style = MaterialTheme.typography.titleMedium
            )

            // EXEMPLO DE CAMPOS
            Text("Universidade: ${u.university}")
            Text("Empresa: ${u.company.name}")
            Text("Cargo: ${u.company.title}")

            Spacer(Modifier.height(24.dp))

            Button(onClick = { nav.popBackStack() }) {
                Text("Voltar")
            }
        }
    }
}
