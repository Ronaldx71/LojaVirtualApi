package com.example.lojavirtualapi.ui.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.danilloteles.appnetflixapi.ui.theme.BLACK
import com.example.lojavirtualapi.ui.posts.material3expressive.MeuLoading
import com.example.lojavirtualapi.ui.posts.material3expressive.MeuCard
import com.example.lojavirtualapi.ui.viewmodel.PostViewModel
import kotlinx.coroutines.delay

@Composable
fun PostListScreen(
    nav: NavController,
    viewModel: PostViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val totalPosts by viewModel.totalPosts.collectAsState()
    val isTranslationEnabled by viewModel.isTranslationEnabled.collectAsState()
    val isModelDownloaded by viewModel.isModelDownloaded.collectAsState()

    LaunchedEffect(searchText) {
        delay(500)
        viewModel.search(searchText)
    }

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
                text = "Postagens",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = BLACK
            )

            // Botão para alternar tradução
            IconButton(
                onClick = { viewModel.toggleTranslation() },
                enabled = isModelDownloaded
            ) {
                Icon(
                    Icons.Default.Language,
                    contentDescription = if (isTranslationEnabled) "Desativar tradução" else "Ativar tradução",
                    tint = if (isTranslationEnabled) BLACK else BLACK.copy(alpha = 0.3f)
                )
            }
        }

        // Indicador de status da tradução
        if (!isModelDownloaded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = BLACK
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Baixando modelo de tradução...",
                    style = MaterialTheme.typography.bodySmall,
                    color = BLACK.copy(alpha = 0.7f)
                )
            }
        } else if (isTranslationEnabled) {
            Text(
                text = "✓ Tradução ativada (EN → PT)",
                style = MaterialTheme.typography.bodySmall,
                color = BLACK.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar posts na API...") },
            leadingIcon = {
                if (posts.loadState.refresh is LoadState.Loading && searchText.isNotEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = BLACK
                    )
                } else {
                    Icon(Icons.Default.Search, contentDescription = "Buscar", tint = BLACK)
                }
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpar", tint = BLACK)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BLACK,
                unfocusedBorderColor = BLACK.copy(alpha = 0.5f),
                focusedLabelColor = BLACK,
                unfocusedLabelColor = BLACK.copy(alpha = 0.7f)
            ),
            maxLines = 1
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (searchText.isEmpty()) {
                    "Total: $totalPosts posts (paginação de 30)"
                } else {
                    "Resultados da busca"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = BLACK.copy(alpha = 0.7f)
            )
        }

        if (posts.loadState.refresh is LoadState.Loading && posts.itemCount == 0) {
            Box(
                modifier = Modifier.fillMaxSize(), // Ocupa todo espaço restante
                contentAlignment = Alignment.Center
            ) {
                MeuLoading()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = posts.itemCount,
                    key = posts.itemKey { it.id }
                ) { index ->
                    val post = posts[index]
                    post?.let {
                        MeuCard(
                            onClick = { nav.navigate("post/${post.id}") },
                            postId = post.id,
                            title = post.displayTitle,
                            body = post.displayBody
                        )
                    }
                }

                when (posts.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                MeuLoading()
                            }
                        }
                    }
                    is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Erro ao carregar mais posts",
                                    color = BLACK.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                    else -> {}
                }

                if (posts.loadState.refresh is LoadState.NotLoading &&
                    posts.itemCount == 0 &&
                    searchText.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "🔍",
                                    style = MaterialTheme.typography.headlineLarge
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Nenhum post encontrado",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = BLACK
                                )
                                Text(
                                    text = "Tente buscar por outro termo",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = BLACK.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PostListScreenPreview(){
    PostListScreen(
        nav = NavController(LocalContext.current),
        onBackClick = {}
    )
}