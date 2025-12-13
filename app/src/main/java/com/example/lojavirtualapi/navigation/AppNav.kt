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

@Composable
fun AppNav(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(navController)
        }

        composable("products") {
            ProductListScreen(navController)
        }

        composable("product/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")!!.toInt()
            ProductDetailScreen(id, navController)
        }

        composable("carts") {
            CartListScreen(navController)
        }

        composable("cart/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")!!.toInt()
            CartDetailScreen(id, navController)
        }

        composable("posts") {
            PostListScreen(navController, onBackClick = { navController.popBackStack() })
        }

        composable("post/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")!!.toInt()
            PostDetailScreen(id, onBackClick = { navController.popBackStack() })
        }
    }
}
