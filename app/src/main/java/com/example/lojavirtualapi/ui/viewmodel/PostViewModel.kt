@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.lojavirtualapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.lojavirtualapi.extensions.translate
import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.repository.PostRepository
import com.example.lojavirtualapi.translation.TranslationManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = PostRepository()
    private val translationManager = TranslationManager.getInstance()
    private val _searchQuery = MutableStateFlow("")

    private val _totalPosts = MutableStateFlow(0)
    val totalPosts = _totalPosts.asStateFlow()

    // Estado para controlar se a tradução está habilitada
    private val _isTranslationEnabled = MutableStateFlow(true)
    val isTranslationEnabled = _isTranslationEnabled.asStateFlow()

    // Estado para saber se o modelo foi baixado
    private val _isModelDownloaded = MutableStateFlow(false)
    val isModelDownloaded = _isModelDownloaded.asStateFlow()

    init {
        // Baixar modelo de tradução ao iniciar
        viewModelScope.launch {
            val downloaded = translationManager.downloadModelIfNeeded()
            _isModelDownloaded.value = downloaded
        }
    }

    val posts: Flow<PagingData<Post>> = _searchQuery.flatMapLatest { query ->
        repository.getPosts(
            query = query,
            onTotal = { total ->
                _totalPosts.value = total
            }
        )
    }.map { pagingData ->
        // Se a tradução estiver habilitada, traduz cada post
        if (_isTranslationEnabled.value) {
            pagingData.map { post ->
                post.translate(translationManager)
            }
        } else {
            pagingData
        }
    }.cachedIn(viewModelScope)

    fun search(query: String) {
        _searchQuery.value = query
    }

    // Função para alternar tradução
    fun toggleTranslation() {
        _isTranslationEnabled.value = !_isTranslationEnabled.value
        // Recarrega os posts com ou sem tradução
        _searchQuery.value = _searchQuery.value
    }

    override fun onCleared() {
        super.onCleared()
        // Liberar recursos do tradutor quando o ViewModel for destruído
        translationManager.close()
    }
}