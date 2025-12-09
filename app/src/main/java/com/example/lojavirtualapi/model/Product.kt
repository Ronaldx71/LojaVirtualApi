package com.example.lojavirtualapi.model

data class ProductResponse(
    val products: List<Product>
)

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String
)
