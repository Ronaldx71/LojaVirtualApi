package com.example.lojavirtualapi.model

data class PostsResponse(
    val posts: List<Post>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
