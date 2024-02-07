package com.example.comics.presentation.pages.list

import com.example.comics.domain.models.Comic

interface IComicView {

    fun viewList(list: List<Comic>)

    fun refrash()

    fun error(message: String)

}