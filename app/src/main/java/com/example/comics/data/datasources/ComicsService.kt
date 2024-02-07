package com.example.comics.data.datasources

import com.example.comics.data.models.ComicsResponse
import com.example.comics.util.Constants.END_POINT_COMICS
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsService {
    @GET(END_POINT_COMICS)
    suspend fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): ComicsResponse
}