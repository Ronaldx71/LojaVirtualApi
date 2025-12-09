@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.lojavirtualapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.repository.PostRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class PostViewModel : ViewModel() {

    private val repository = PostRepository()
    private val _searchQuery = MutableStateFlow("")

    private val _totalPosts = MutableStateFlow(0)
    val totalPosts = _totalPosts.asStateFlow()

    val posts: Flow<PagingData<Post>> = _searchQuery.flatMapLatest { query ->
        repository.getPosts(
            query = query,
            onTotal = { total ->
                _totalPosts.value = total
            }
        )
    }.cachedIn(viewModelScope)

    fun search(query: String) {
        _searchQuery.value = query
    }
}