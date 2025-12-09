package com.example.lojavirtualapi.model

data class CartResponse(
    val carts: List<Cart>
)

data class Cart(
    val id: Int,
    val products: List<CartProduct>,
    val total: Int,
    val discountedTotal: Int,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
)

data class CartProduct(
    val id: Int,
    val title: String,
    val price: Int,
    val quantity: Int,
    val total: Int,
    val discountPercentage: Double,
    val discountedPrice: Int
)
