package com.example.lojavirtualapi.ui.posts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danilloteles.appnetflixapi.ui.theme.BLACK
import com.danilloteles.appnetflixapi.ui.theme.WHITE
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.ui.posts.material3expressive.Dislike
import com.example.lojavirtualapi.ui.posts.material3expressive.Like
import com.example.lojavirtualapi.ui.posts.material3expressive.MeuLoading
import com.example.lojavirtualapi.ui.posts.material3expressive.Visualizacoes
import java.util.Locale

@Composable
fun PostDetailScreen(
    id: Int,
    onBackClick: () -> Unit
) {

    var post by remember { mutableStateOf<Post?>(null) }

    LaunchedEffect(id) {
        post = RetrofitInstance.api.getPost(id)
    }

    post?.let { post ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = BLACK
                    )
                }

                Text(
                    text = "Detalhes da Postagem",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = BLACK
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Usuário: ${post.userId}",
                    style = MaterialTheme.typography.titleLarge,
                    color = BLACK
                )

                Surface(
                    color = BLACK,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Postagem ID: ${post.id}",
                        style = MaterialTheme.typography.titleMedium,
                        color = WHITE,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.headlineSmall,
                color = BLACK,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.body.replace("\n", "\n\n"),
                style = MaterialTheme.typography.bodyMedium,
                color = BLACK,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Visualizacoes(
                    visualizacoes = post.views,
                )

                Like(
                    likes = post.reactions.likes,
                )

                Dislike(
                    dislikes = post.reactions.dislikes,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tags:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(post.tags) { tag ->
                    Surface(
                        color = WHITE,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, BLACK),
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Text(
                            text = tag.uppercase(Locale.ROOT),
                            color = BLACK,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MeuLoading()
    }

}

@Preview
@Composable
private fun PostDetailScreenPreview() {
    PostDetailScreen(
        id = 3,
        onBackClick = {}
    )
}