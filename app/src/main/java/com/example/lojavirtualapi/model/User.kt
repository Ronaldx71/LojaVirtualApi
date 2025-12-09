package com.example.lojavirtualapi.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String,
    val phone: String,
    val image: String,
    val address: Address,
    val university: String?,
    val company: Company
)

data class Address(
    val address: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
)

data class Company(
    val name: String,
    val title: String,
    val department: String
)
