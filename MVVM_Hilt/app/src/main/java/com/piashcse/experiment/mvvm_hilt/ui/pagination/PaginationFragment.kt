package com.piashcse.experiment.mvvm_hilt.ui.pagination

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.paginate.Paginate
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentPaginationBinding
import com.piashcse.experiment.mvvm_hilt.ui.pagination.adapter.MoviePagingAdapter
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import com.piashcse.experiment.mvvm_hilt.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaginationFragment : BaseBindingFragment<FragmentPaginationBinding>() {
    private val paginationViewModel: PaginationViewModel by viewModels()
    private val moviePagingAdapter: MoviePagingAdapter by lazy {
        MoviePagingAdapter()
    }
    private var loadingInProgress = true
    private var hasLoadedAllItems = false
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paginationViewModel.popularMovie(page)
    }

    override fun init() {
        binding.apply {
            paginationRecycler.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = moviePagingAdapter
            }
            val callbacks: Paginate.Callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    // Load next page of data (e.g. network or database)
                    lifecycleScope.launch {
                        paginationViewModel.popularMovie(++page)
                    }
                }

                override fun isLoading(): Boolean {
                    // Indicate whether new page loading is in progress or not
                    return loadingInProgress
                }

                override fun hasLoadedAllItems(): Boolean {
                    // Indicate whether all data (pages) are loaded or not
                    return hasLoadedAllItems
                }
            }
            Paginate.with(paginationRecycler, callbacks)
                .setLoadingTriggerThreshold(1)
                .addLoadingListItem(false)
                .build()
        }
        apiResponse()
    }

    private fun apiResponse() = with(binding) {
        paginationViewModel.popularMovieResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    progressBar.show()
                    loadingInProgress = true
                }
                is DataState.Success -> {
                    progressBar.hide()
                    moviePagingAdapter.addItems(it.data)
                    loadingInProgress = false
                    if (it.data.isEmpty())
                        hasLoadedAllItems = true
                }
                is DataState.Error -> {
                    progressBar.hide()
                    loadingInProgress = false
                }
            }
        }
    }

}