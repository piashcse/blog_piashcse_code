package com.piashcse.experiment.mvvm_hilt.ui.paging3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentPaging3Binding
import com.piashcse.experiment.mvvm_hilt.ui.paging3.adapter.MoviePagingAdapter
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class Paging3Fragment : Fragment() {
    private var _binding: FragmentPaging3Binding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: Paging3ViewModel by viewModels()
    private val moviePagingAdapter: MoviePagingAdapter by lazy {
        MoviePagingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPaging3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.catch {
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
            Timber.e("OK OK")
            findNavController().navigate(R.id.readJsonFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}