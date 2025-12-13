package com.example.lojavirtualapi.api

import com.example.lojavirtualapi.model.Product
import com.example.lojavirtualapi.model.ProductResponse
import com.example.lojavirtualapi.model.CartResponse
import com.example.lojavirtualapi.model.Cart
import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.model.PostResponse
import com.example.lojavirtualapi.model.User
import com.example.lojavirtualapi.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyApi {

    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @GET("carts")
    suspend fun getCarts(): CartResponse

    @GET("carts/{id}")
    suspend fun getCart(@Path("id") id: Int): Cart

    @GET("users")
    suspend fun getUsers(): UsersResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    @GET("posts")
    suspend fun getPosts(
        @Query("limit") limit: Int = 30,
        @Query("skip") skip: Int = 0,
        @Query("q") query: String = ""
    ): PostResponse

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post

    @GET("posts/search")
    suspend fun searchPosts(
        @Query("q") query: String,
        @Query("limit") limit: Int = 30,
        @Query("skip") skip: Int = 0
    ): PostResponse

}
