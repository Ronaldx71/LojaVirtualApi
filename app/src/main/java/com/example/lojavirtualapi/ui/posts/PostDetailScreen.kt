package com.example.lojavirtualapi.ui.posts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danilloteles.appnetflixapi.ui.theme.BLACK
import com.danilloteles.appnetflixapi.ui.theme.WHITE
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.extensions.translate
import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.translation.TranslationManager
import com.example.lojavirtualapi.ui.posts.material3expressive.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    id: Int,
    onBackClick: () -> Unit
) {
    var post by remember { mutableStateOf<Post?>(null) }
    val translationManager = remember { TranslationManager.getInstance() }

    LaunchedEffect(id) {
        val loadedPost = RetrofitInstance.api.getPost(id)
        post = loadedPost.translate(translationManager)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhe da Postagem") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->

        post?.let { post ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

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
                    text = post.displayTitle,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = BLACK
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = post.displayBody.replace("\n", "\n\n"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = BLACK,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Visualizacoes(post.views)
                    Like(post.reactions.likes)
                    Dislike(post.reactions.dislikes)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Tags:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(post.displayTags) { tag ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, BLACK),
                            color = WHITE
                        ) {
                            Text(
                                text = tag.uppercase(Locale.ROOT),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                if (post.titlePt != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✓ Conteúdo traduzido do inglês",
                        style = MaterialTheme.typography.bodySmall,
                        color = BLACK.copy(alpha = 0.5f)
                    )
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            MeuLoading()
        }
    }
}

@Preview
@Composable
private fun PostDetailScreenPreview(){
    PostDetailScreen(
        id = 1,
        onBackClick = {}
    )
}