package com.example.lojavirtualapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lojavirtualapi.ui.home.HomeScreen
import com.example.lojavirtualapi.ui.products.ProductListScreen
import com.example.lojavirtualapi.ui.products.ProductDetailScreen
import com.example.lojavirtualapi.ui.carts.CartListScreen
import com.example.lojavirtualapi.ui.carts.CartDetailScreen
import com.example.lojavirtualapi.ui.posts.PostDetailScreen
import com.example.lojavirtualapi.ui.posts.PostListScreen
import com.example.lojavirtualapi.ui.users.UsersListScreen
import com.example.lojavirtualapi.ui.users.UserDetailScreen



@Composable
fun AppNav(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController)
        }

        composable("products") {
            ProductListScreen(navController)
        }

        composable("product/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")!!.toInt()
            ProductDetailScreen(
                id = id,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("carts") {
            CartListScreen(navController)
        }

        composable("cart/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")!!.toInt()
            CartDetailScreen(
                id = id,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("users") {
            UsersListScreen(navController)
        }

        composable("user/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")!!.toInt()
            UserDetailScreen(
                id = id,
                onBackClick = { navController.popBackStack() }
            )
        }




        composable("posts") {
            PostListScreen(
                navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("post/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")!!.toInt()
            PostDetailScreen(
                id = id,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

