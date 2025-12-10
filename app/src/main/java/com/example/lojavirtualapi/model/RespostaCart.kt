package com.example.lojavirtualapi.model

data class RespostaCart(
    val carts: List<Cart>,
    val limit: Int,
    val skip: Int,
    val total: Int
)