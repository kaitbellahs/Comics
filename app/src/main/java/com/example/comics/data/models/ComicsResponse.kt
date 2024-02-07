package com.example.comics.data.models

import com.google.gson.annotations.SerializedName

data class ComicsResponse(
    val data: DataModel
)

data class DataModel(
    @SerializedName("results")
    val comics: List<Comic>
)

data class Comic(
    val title: String,
    val description: String?,
    val thumbnail: ThumbnailModel
)

data class ThumbnailModel(
    val path: String,
    val extension: String,
)