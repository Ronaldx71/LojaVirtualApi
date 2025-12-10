package com.example.lojavirtualapi.translation

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

class TranslationManager {

    private var translatorEnToPt: Translator? = null
    private var translatorPtToEn: Translator? = null
    private var isModelDownloaded = false

    // Tradutor EN → PT
    private fun getTranslatorEnToPt(): Translator {
        if (translatorEnToPt == null) {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.PORTUGUESE)
                .build()
            translatorEnToPt = Translation.getClient(options)
        }
        return translatorEnToPt!!
    }

    // Tradutor PT → EN (para busca)
    private fun getTranslatorPtToEn(): Translator {
        if (translatorPtToEn == null) {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.PORTUGUESE)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()
            translatorPtToEn = Translation.getClient(options)
        }
        return translatorPtToEn!!
    }

    // Baixar modelo se necessário (apenas uma vez)
    suspend fun downloadModelIfNeeded(): Boolean {
        return try {
            if (!isModelDownloaded) {
                // Baixar ambos os modelos
                getTranslatorEnToPt().downloadModelIfNeeded().await()
                getTranslatorPtToEn().downloadModelIfNeeded().await()
                isModelDownloaded = true
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Traduzir texto único EN → PT
    suspend fun translate(text: String): String {
        return try {
            if (text.isBlank()) return text

            // Garantir que o modelo está baixado
            downloadModelIfNeeded()

            // Traduzir EN → PT
            getTranslatorEnToPt().translate(text).await()
        } catch (e: Exception) {
            e.printStackTrace()
            text // Retorna texto original em caso de erro
        }
    }

    // Traduzir texto PT → EN (para busca)
    suspend fun translateToEnglish(text: String): String {
        return try {
            if (text.isBlank()) return text

            // Garantir que o modelo está baixado
            downloadModelIfNeeded()

            // Traduzir PT → EN
            getTranslatorPtToEn().translate(text).await()
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
                    getTranslatorEnToPt().translate(text).await()
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
                getTranslatorEnToPt().translate(tag).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            tags
        }
    }

    // Liberar recursos quando não precisar mais
    fun close() {
        translatorEnToPt?.close()
        translatorPtToEn?.close()
        translatorEnToPt = null
        translatorPtToEn = null
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