package com.example.futurama

import androidx.paging.*
import com.example.futurama.classes.Character
import kotlinx.coroutines.Dispatchers

class ListPagedSource(
    private val viewModel: ListViewModel
) : PagingSource<Int, com.example.futurama.classes.Character>() {
    override fun getRefreshKey(state: PagingState<Int, com.example.futurama.classes.Character>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> =
        kotlin.runCatching {
            viewModel.loadCharacter(params.key ?: 1)
        }.fold(
            onSuccess = {list ->
                LoadResult.Page(
                    data = list,
                    prevKey = params.key?.let { it - 1 },
                    nextKey = (params.key ?: 1) + 1
                )
            },
            onFailure = {throwable -> LoadResult.Error(throwable)}
        )

    companion object {
        fun pager(viewModel: ListViewModel) = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ListPagedSource(viewModel) }
        )
    }
}