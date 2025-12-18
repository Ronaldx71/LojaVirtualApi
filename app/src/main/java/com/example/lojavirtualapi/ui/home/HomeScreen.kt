package com.example.lojavirtualapi.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojavirtualapi.ui.posts.material3expressive.CardHome


@Composable
fun HomeScreen(nav: NavController) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(4) { item ->
                when (item) {
                    0 -> CardHome(
                        onClick = { nav.navigate("products") },
                        title = "Produtos"
                    )
                    1 -> CardHome(
                        onClick = { nav.navigate("carts") },
                        title = "Carrinho"
                    )
                    2 -> CardHome(
                        onClick = { nav.navigate("users") },
                        title = "Usuários"
                    )
                    3 -> CardHome(
                        onClick = { nav.navigate("posts") },
                        title = "Postagens"
                    )
                }
            }
        }
    }
}

