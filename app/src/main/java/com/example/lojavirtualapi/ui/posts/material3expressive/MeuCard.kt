package com.example.lojavirtualapi.ui.posts.material3expressive

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MeuCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    postId: Int,
    title: String,
    body: String
){
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = "Post ID: $postId",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Título: $title",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Corpo: $body",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun MeuCardPreview(){
    MeuCard(
        postId = 1,
        title = "Título do Post",
        body = "Este é o corpo do post de exemplo para visualização no preview.",
        onClick = {}
    )
}