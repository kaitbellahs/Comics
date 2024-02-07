package com.example.comics.presentation.pages

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.databinding.ActivityMainBinding
import com.example.comics.domain.models.Comic
import com.example.comics.presentation.adapters.ComicsListAdapter
import com.example.comics.presentation.pages.list.ComicsListViewModel
import com.example.comics.presentation.pages.list.IComicView
import com.example.comics.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IComicView {
    val viewModel: ComicsListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        observeData()
        refrash()

        swipeList()
    }

    private fun observeData() {
        lifecycleScope.launch {

            viewModel.comicsState.collect {

                when (it.status) {

                    Status.SUCCESS -> {
                        it.data?.let { comicsResponse ->
                            val list: List<Comic> = comicsResponse.map {
                                Comic(

                                    image = "${it.thumbnail.path}.${it.thumbnail.extension}",
                                    title = it.title,
                                    subtitle = it.description ?: "Sem descricao"
                                )
                            }
                            with(binding) {
                                this?.errorTV?.visibility = View.GONE
                                this?.listItem?.visibility = View.VISIBLE
                                this?.listItem?.adapter = ComicsListAdapter(list)
                                this?.listItem?.layoutManager =
                                    LinearLayoutManager(this@MainActivity)
                                this?.swipeRefresh?.isRefreshing = false
                            }
                        }
                    }

                    else -> {
                        it.message?.let { it1 -> error(it1) }
                    }
                }
            }
        }
    }

    private var binding: ActivityMainBinding? = null


    private fun swipeList() = with(binding?.swipeRefresh) {
        this?.setOnRefreshListener {
            refrash()
        }
    }

    override fun refrash() {
        with(binding) {
            this?.swipeRefresh?.isRefreshing = true
            lifecycle.coroutineScope.launch {
                viewModel.fetchComics()
            }
        }
    }

    override fun viewList(list: List<Comic>) {
        with(binding) {
            this?.errorTV?.visibility = View.GONE
            this?.listItem?.visibility = View.VISIBLE
            this?.listItem?.adapter = ComicsListAdapter(list)
            this?.listItem?.layoutManager = LinearLayoutManager(this@MainActivity)
            this?.swipeRefresh?.isRefreshing = false
        }
    }

    override fun error(message: String) {
        with(binding) {
            this?.listItem?.visibility = View.GONE
            this?.errorTV?.visibility = View.VISIBLE
            this?.errorTV?.text = message
            this?.swipeRefresh?.isRefreshing = false
        }
    }
}