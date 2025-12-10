package com.example.lojavirtualapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ProductResponse(
    val products: List<Product>
)
@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String
): Parcelable
