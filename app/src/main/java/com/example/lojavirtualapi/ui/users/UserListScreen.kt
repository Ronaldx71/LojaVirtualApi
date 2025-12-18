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
@Suppress("Unused")
@Composable
fun UsersListScreen(nav: NavController) {

    var list by remember { mutableStateOf<List<User>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val response = RetrofitInstance.api.getUsers()
        list = response.users
        loading = false
    }

    if (loading) {
        CircularProgressIndicator()
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(list) { user ->
            Card(
                onClick = {
                    nav.navigate("user/${user.id}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
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
                            text = "${user.firstName} ${user.lastName}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(user.email)
                    }
                }
            }
        }
    }
}
