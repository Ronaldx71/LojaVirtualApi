package com.example.lojavirtualapi.extensions

import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.translation.TranslationManager

// Extension function para traduzir um Post
suspend fun Post.translate(translationManager: TranslationManager): Post {
    // Se já está traduzido, retorna o mesmo
    if (titlePt != null && bodyPt != null && tagsPt != null) {
        return this
    }

    return try {
        // Traduzir title, body e tags em paralelo seria mais eficiente,
        // mas para simplicidade vamos fazer sequencial
        val translatedTitle = translationManager.translate(title)
        val translatedBody = translationManager.translate(body)
        val translatedTags = translationManager.translateTags(tags)

        // Retornar cópia do post com traduções
        copy(
            titlePt = translatedTitle,
            bodyPt = translatedBody,
            tagsPt = translatedTags
        )
    } catch (e: Exception) {
        e.printStackTrace()
        // Em caso de erro, retorna o post original
        this
    }
}

// Extension function para traduzir uma lista de Posts
suspend fun List<Post>.translateAll(translationManager: TranslationManager): List<Post> {
    return map { it.translate(translationManager) }
}