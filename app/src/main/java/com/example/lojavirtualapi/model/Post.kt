package com.example.lojavirtualapi.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val views: Int,
    val tags: List<String>,
    val reactions: Reactions
)

data class Reactions(
    val dislikes: Int,
    val likes: Int
)

data class PostResponse(
    val posts: List<Post>
)
