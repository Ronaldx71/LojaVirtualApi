package com.example.lojavirtualapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CartResponse(
    val carts: List<Cart>
)
@Parcelize
data class Cart(
    val id: Int,
    val products: List<CartProduct>,
    val total: Int,
    val discountedTotal: Int,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
): Parcelable
@Parcelize
data class CartProduct(
    val id: Int,
    val title: String,
    val price: Int,
    val quantity: Int,
    val total: Int,
    val discountPercentage: Double,
    val discountedPrice: Int
): Parcelable
