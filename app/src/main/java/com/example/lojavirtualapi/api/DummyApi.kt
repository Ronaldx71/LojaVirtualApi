package com.example.lojavirtualapi.api

import com.example.lojavirtualapi.model.Product
import com.example.lojavirtualapi.model.ProductResponse
import com.example.lojavirtualapi.model.CartResponse
import com.example.lojavirtualapi.model.Cart
import com.example.lojavirtualapi.model.UsersResponse
import com.example.lojavirtualapi.model.User
import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.model.PostsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DummyApi {

    // -------- PRODUCTS ----------
    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    // -------- CARTS ----------
    @GET("carts")
    suspend fun getCarts(): CartResponse

    @GET("carts/{id}")
    suspend fun getCart(@Path("id") id: Int): Cart

    // -------- USERS ----------
    @GET("users")
    suspend fun getUsers(): UsersResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    @GET("posts")
    suspend fun getPosts(): PostsResponse

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post

}
