package com.example.lojavirtualapi.ui.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
fun PostListScreen(
    nav: NavController
) {

    var list by remember { mutableStateOf<List<Post>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val response = RetrofitInstance.api.getPosts()
        list = response.posts
        loading = false
    }

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(list) { post ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { nav.navigate("post/${post.id}")}
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Post ID: ${post.id}", style = MaterialTheme.typography.titleMedium)
                    Text("Titulo: ${post.title}")
                    Text("Corpo: ${post.body}")
                }
            }
        }
    }

}

@Preview
@Composable
private fun PostListScreenPreview(){
    PostListScreen(
        nav = NavController(LocalContext.current)
    )
}