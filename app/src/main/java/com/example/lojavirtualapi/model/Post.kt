package com.example.lojavirtualapi.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val tags: List<String>,
    val reactions: Int
)

data class PostResponse(
    val posts: List<Post>
)
