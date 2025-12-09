package com.example.lojavirtualapi.ui.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Post

@Composable
fun PostDetailScreen(
    id: Int,
    nav: NavController
) {

    var post by remember { mutableStateOf<Post?>(null) }

    LaunchedEffect(id) {
        post = RetrofitInstance.api.getPost(id)
    }

    post?.let { post ->

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Usuário: ${post.userId}" , style = MaterialTheme.typography.titleLarge)
            Text("Postagem ID: ${post.id}", style = MaterialTheme.typography.titleMedium)
            Text("Titulo: ${post.title}")
            Text("Corpo: ${post.body}")
            Text("Visualizações: ${post.views}")
            Text("Tags: ${post.tags.joinToString(", ")}")
            Text("Likes: ${post.reactions.likes}")
            Text("Dislikes: ${post.reactions.dislikes}")
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

}

@Preview
@Composable
private fun PostDetailScreenPreview(){
    PostDetailScreen(
        id = 3,
        nav = NavController(LocalContext.current)
    )
}