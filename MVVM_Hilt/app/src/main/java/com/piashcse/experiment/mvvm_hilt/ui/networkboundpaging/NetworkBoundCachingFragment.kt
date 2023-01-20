package com.piashcse.experiment.mvvm_hilt.ui.networkboundpaging

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentNeworkBoundCachingBinding
import com.piashcse.experiment.mvvm_hilt.ui.pagin3caching.Paging3CachingViewModel
import com.piashcse.experiment.mvvm_hilt.ui.pagin3caching.adapter.MoviePagingCachingAdapter
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.show
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NetworkBoundCachingFragment : BaseBindingFragment<FragmentNeworkBoundCachingBinding>() {
    private val viewModel: NetworkBoundViewModel by viewModels()
    private val moviePagingAdapter: MoviePagingCachingAdapter by lazy {
        MoviePagingCachingAdapter()
    }

    override fun init() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
               /* viewModel.refresh(true).catch {
                    progressBar.hide()
                }.collectLatest {
                    moviePagingAdapter.submitData(it)
                }*/
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