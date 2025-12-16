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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = PostRepository()
    private val translationManager = TranslationManager.getInstance()

    private val _searchQuery = MutableStateFlow("")

    private val _totalPosts = MutableStateFlow(0)
    val totalPosts = _totalPosts.asStateFlow()

    private val _isTranslationEnabled = MutableStateFlow(true)
    val isTranslationEnabled = _isTranslationEnabled.asStateFlow()

    private val _isModelDownloaded = MutableStateFlow(false)
    val isModelDownloaded = _isModelDownloaded.asStateFlow()

    init {
        viewModelScope.launch {
            val downloaded = translationManager.downloadModelIfNeeded()
            _isModelDownloaded.value = downloaded
        }
    }

    val posts: Flow<PagingData<Post>> = _searchQuery.flatMapLatest { query ->
        val searchQuery = if (query.isNotEmpty() && _isTranslationEnabled.value) {
            viewModelScope.async {
                translationManager.translateToEnglish(query)
            }.await()
        } else {
            query
        }

        repository.getPosts(
            query = searchQuery,
            onTotal = { total ->
                _totalPosts.value = total
            }
        )
    }.map { pagingData ->
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

    fun toggleTranslation() {
        _isTranslationEnabled.value = !_isTranslationEnabled.value
        _searchQuery.value = _searchQuery.value
    }

}