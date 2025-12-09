package com.example.lojavirtualapi.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lojavirtualapi.api.DummyApi
import com.example.lojavirtualapi.model.Post

class PostPagingSource(
    private val dummyApi: DummyApi,
    private val query: String = ""
) : PagingSource<Int, Post>() {

    companion object {
        const val PAGE_SIZE = 30
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val skip = params.key ?: 0

            val response = if (query.isEmpty()) {
                dummyApi.getPosts(limit = PAGE_SIZE, skip = skip)
            } else {
                dummyApi.searchPosts(query = query, limit = PAGE_SIZE, skip = skip)
            }

            LoadResult.Page(
                data = response.posts,
                prevKey = if (skip == 0) null else skip - PAGE_SIZE,
                nextKey = if (response.posts.isEmpty()) null else skip + PAGE_SIZE
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(PAGE_SIZE) ?: anchorPage?.nextKey?.minus(PAGE_SIZE)
        }
    }
}