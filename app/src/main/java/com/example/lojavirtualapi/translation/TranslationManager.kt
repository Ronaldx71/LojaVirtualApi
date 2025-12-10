package com.example.lojavirtualapi.translation

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

class TranslationManager {

    private var translator: Translator? = null
    private var isModelDownloaded = false

    // Inicializar o tradutor
    private fun getTranslator(): Translator {
        if (translator == null) {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.PORTUGUESE)
                .build()
            translator = Translation.getClient(options)
        }
        return translator!!
    }

    // Baixar modelo se necessário (apenas uma vez)
    suspend fun downloadModelIfNeeded(): Boolean {
        return try {
            if (!isModelDownloaded) {
                getTranslator().downloadModelIfNeeded().await()
                isModelDownloaded = true
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Traduzir texto único
    suspend fun translate(text: String): String {
        return try {
            if (text.isBlank()) return text

            // Garantir que o modelo está baixado
            downloadModelIfNeeded()

            // Traduzir
            getTranslator().translate(text).await()
        } catch (e: Exception) {
            e.printStackTrace()
            text // Retorna texto original em caso de erro
        }
    }

    // Traduzir múltiplos textos de uma vez (mais eficiente)
    suspend fun translateMultiple(texts: List<String>): List<String> {
        return try {
            downloadModelIfNeeded()

            texts.map { text ->
                if (text.isBlank()) {
                    text
                } else {
                    getTranslator().translate(text).await()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            texts // Retorna textos originais em caso de erro
        }
    }

    // Traduzir tags
    suspend fun translateTags(tags: List<String>): List<String> {
        return try {
            downloadModelIfNeeded()

            tags.map { tag ->
                getTranslator().translate(tag).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            tags
        }
    }

    // Liberar recursos quando não precisar mais
    fun close() {
        translator?.close()
        translator = null
        isModelDownloaded = false
    }

    companion object {
        // Singleton para reutilizar a mesma instância
        @Volatile
        private var instance: TranslationManager? = null

        fun getInstance(): TranslationManager {
            return instance ?: synchronized(this) {
                instance ?: TranslationManager().also { instance = it }
            }
        }
    }
}