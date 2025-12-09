package com.example.lojavirtualapi.ui.posts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Post

@Composable
fun PostDetailScreen(id: Int, nav: NavController) {

    var post by remember { mutableStateOf<Post?>(null) }
    var loading by remember { mutableStateOf(true) }

    // ----- API CALL -----
    LaunchedEffect(id) {
        post = RetrofitInstance.api.getPost(id)
        loading = false
    }

    // ----- LOADING -----
    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val p = post ?: return

    // ----- MAIN LAYOUT -----
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = p.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = p.body,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Tags:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        Row {
            p.tags.forEach { tag ->
                AssistChip(
                    onClick = {},
                    label = { Text("#$tag") },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Reações: ${p.reactions}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(32.dp))

        Button(onClick = { nav.popBackStack() }) {
            Text("Voltar")
        }
    }
}
