package com.example.lojavirtualapi.ui.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Post

@Composable
fun PostsListScreen(nav: NavController) {

    var list by remember { mutableStateOf<List<Post>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    // --- Carrega posts da API ---
    LaunchedEffect(Unit) {
        val response = RetrofitInstance.api.getPosts()
        list = response.posts
        loading = false
    }

    // --- Loading indicator ---
    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // --- Lista de posts ---
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(list) { post ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { nav.navigate("post/${post.id}") },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Text(
                        text = post.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = post.body.take(120) + "...",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(Modifier.height(8.dp))

                    Row {
                        post.tags.forEach { tag ->
                            AssistChip(
                                onClick = {},
                                label = { Text("#$tag") },
                                modifier = Modifier.padding(end = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
