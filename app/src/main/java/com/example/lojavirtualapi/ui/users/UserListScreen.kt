package com.example.lojavirtualapi.ui.users
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.User
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment


@Composable
fun UsersListScreen(nav: NavController) {

    var list by remember { mutableStateOf<List<User>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getUsers()
            list = response.users
        } catch (e: Exception) {
            error = "Erro ao carregar usuários"
        } finally {
            loading = false
        }
    }

    when {
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(error ?: "Erro inesperado")
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(list) { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clickable {
                                nav.navigate("user/${user.id}")
                            }
                    ) {
                        Row(Modifier.padding(16.dp)) {
                            AsyncImage(
                                model = user.image,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(
                                    "${user.firstName} ${user.lastName}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(user.email)
                            }
                        }
                    }


                    Spacer(Modifier.width(16.dp))

                            Column {
                                Text(
                                    "${user.firstName} ${user.lastName}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(user.email)
                            }
                        }
                    }
                }
            }
        }

