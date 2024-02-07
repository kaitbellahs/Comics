package com.example.comics.data.repositories

import com.example.comics.data.datasources.ComicsService
import com.example.comics.data.models.Comic
import com.example.comics.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ComicRepository @Inject constructor(private val comicsService: ComicsService) {

    suspend fun fetchComicsData(): Flow<ViewState<List<Comic>>> {
        return flow {

            val response = comicsService.getComics(
                apikey = "b7e14bab409c70a5c4e7c2b319c09d7b",
                ts = "1682982412",
                hash = "3482f01e9bf207a437a4b621c91339ad"
            )
            val comics = response.data.comics
            emit(ViewState.success(comics))
        }.flowOn(Dispatchers.IO)
    }
}