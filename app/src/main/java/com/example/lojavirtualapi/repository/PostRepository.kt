package com.example.lojavirtualapi.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.lojavirtualapi.api.RetrofitInstance
import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.paging.PostPagingSource
import kotlinx.coroutines.flow.Flow

class PostRepository {

    fun getPosts(query: String = ""): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = PostPagingSource.PAGE_SIZE,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource(RetrofitInstance.api, query) }
        ).flow
    }
}