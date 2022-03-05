package com.piashcse.experiment.mvvm_hilt.ui.pagination

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.paginate.Paginate
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentPaginationBinding
import com.piashcse.experiment.mvvm_hilt.ui.pagination.adapter.MoviePagingAdapter
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import com.piashcse.experiment.mvvm_hilt.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaginationFragment : Fragment() {
    private var _binding: FragmentPaginationBinding? = null
    private val binding get() = requireNotNull(_binding)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPaginationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        apiResponse()
    }

    private fun initView() = with(binding) {
        paginationRecycler.apply {
            layoutManager =  GridLayoutManager(requireContext(), 2)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}