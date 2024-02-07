package com.example.comics.presentation.pages.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.data.models.Comic
import com.example.comics.data.models.ThumbnailModel
import com.example.comics.data.repositories.ComicRepository
import com.example.comics.util.Status
import com.example.comics.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsListViewModel @Inject constructor(private val comicsRepository: ComicRepository) :
    ViewModel() {

    val comicsState = MutableStateFlow(
        ViewState(
            Status.LOADING,
            listOf(Comic("", "", ThumbnailModel("", ""))), ""
        )
    )


    init {
        fetchComics()
    }

    fun fetchComics() {

        comicsState.value = ViewState.loading()

        viewModelScope.launch {
            comicsRepository.fetchComicsData()
                .catch {
                    comicsState.value =
                        ViewState.error(it.message.toString())
                }
                .collect {
                    comicsState.value = ViewState.success(it.data)
                }
        }
    }
}