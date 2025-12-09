package com.example.lojavirtualapi.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

data class PostResponse(
    val posts: List<Post>
)
