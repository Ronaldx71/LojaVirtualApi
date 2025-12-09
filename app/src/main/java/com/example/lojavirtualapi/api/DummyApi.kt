package com.example.lojavirtualapi.api

import com.example.lojavirtualapi.model.Product
import com.example.lojavirtualapi.model.ProductResponse
import com.example.lojavirtualapi.model.CartResponse
import com.example.lojavirtualapi.model.Cart
import retrofit2.http.GET
import retrofit2.http.Path

interface DummyApi {

    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @GET("carts")
    suspend fun getCarts(): CartResponse

    @GET("carts/{id}")
    suspend fun getCart(@Path("id") id: Int): Cart

}
