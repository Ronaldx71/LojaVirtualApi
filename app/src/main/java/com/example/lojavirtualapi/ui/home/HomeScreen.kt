package com.example.lojavirtualapi.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojavirtualapi.ui.posts.material3expressive.CardHome

@Composable
fun HomeScreen(nav: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = { nav.navigate("products") }) {
            Text("Produtos")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { /* carrinhos depois */ }) {
            Text("Carrinhos")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { /* usuários depois */ }) {
            Text("Usuários")
        }
        Button(onClick = { nav.navigate("carts") }) {
            Text("Carrinhos")
        }

        CardHome(
            onClick = { nav.navigate("posts") },
            title = "Postagem"
        )

    }
}
