package com.example.lojavirtualapi.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lojavirtualapi.api.DummyApi
import com.example.lojavirtualapi.model.Post
import com.example.lojavirtualapi.model.PostResponse

class PostPagingSource(
    private val dummyApi: DummyApi,
    private val query: String,
    private val onTotal: (Int) -> Unit
) : PagingSource<Int, Post>() {

    companion object {
        const val PAGE_SIZE = 30
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 0

            val response: PostResponse = dummyApi.getPosts(
                skip = page * PAGE_SIZE,
                limit = PAGE_SIZE,
                query = query
            )

            if (page == 0) {
                onTotal(response.total)
            }

            LoadResult.Page(
                data = response.posts,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.posts.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(PAGE_SIZE) ?: anchorPage?.nextKey?.minus(PAGE_SIZE)
        }
    }
}