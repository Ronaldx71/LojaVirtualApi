package com.example.lojavirtualapi.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val views: Int,
    val tags: List<String>,
    val reactions: Reactions,

    val titlePt: String? = null,
    val bodyPt: String? = null,
    val tagsPt: List<String>? = null
) {
    val displayTitle: String
        get() = titlePt ?: title

    val displayBody: String
        get() = bodyPt ?: body

    val displayTags: List<String>
        get() = tagsPt ?: tags
}

data class Reactions(
    val dislikes: Int,
    val likes: Int
)

data class PostResponse(
    val limit: Int,
    val posts: List<Post>,
    val skip: Int,
    val total: Int
)
