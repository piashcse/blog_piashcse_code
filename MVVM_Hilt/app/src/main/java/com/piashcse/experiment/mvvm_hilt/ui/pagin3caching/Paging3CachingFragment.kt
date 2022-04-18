package com.piashcse.experiment.mvvm_hilt.ui.pagin3caching

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentPaging3CachingBinding
import com.piashcse.experiment.mvvm_hilt.ui.pagin3caching.adapter.MoviePagingCachingAdapter
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Paging3CachingFragment : BaseBindingFragment<FragmentPaging3CachingBinding>() {
    private val viewModel: Paging3CachingViewModel by viewModels()
    private val moviePagingAdapter: MoviePagingCachingAdapter by lazy {
        MoviePagingCachingAdapter()
    }

    override fun init() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.popularPagingMediatorDataSource().catch {
                    progressBar.hide()
                }.collectLatest {
                    moviePagingAdapter.submitData(it)
                }
            }
            pagingRecycler.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = moviePagingAdapter
            }
            moviePagingAdapter.addLoadStateListener {
                when {
                    it.refresh == LoadState.Loading -> {
                        progressBar.show()
                    }
                    it.append == LoadState.Loading -> {
                        progressBar.show()
                    }
                    else -> {
                        progressBar.hide()
                    }
                }
            }
            moviePagingAdapter.onItemClick = {
                findNavController().navigate(R.id.readJsonFragment)
            }
        }
    }
}