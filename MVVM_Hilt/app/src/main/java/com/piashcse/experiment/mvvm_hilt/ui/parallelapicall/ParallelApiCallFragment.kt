package com.piashcse.experiment.mvvm_hilt.ui.parallelapicall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentParallelApiCallBinding
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import com.piashcse.experiment.mvvm_hilt.ui.parallelapicall.adapter.MovieAdapter
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.show
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ParallelApiCallFragment : Fragment() {
    private var _binding: FragmentParallelApiCallBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: ParallelApiCallViewModel by viewModels()
    private val popularMovieAdapter: MovieAdapter by lazy {
        MovieAdapter()
    }
    private val topRatedMovieAdapter: MovieAdapter by lazy {
        MovieAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.popularAndTopRatedMovie()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentParallelApiCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        apiResponse()
    }

    private fun initView() = with(binding) {
        popularRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMovieAdapter
        }
        topRatedRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topRatedMovieAdapter
        }
    }

    private fun apiResponse() {
        viewModel.popularMovie.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    binding.progressbar.show()
                }
                is DataState.Success -> {
                    binding.progressbar.hide()
                    popularMovieAdapter.addItems(it.data)
                }
                is DataState.Error -> {
                    binding.progressbar.hide()
                    Timber.e("Error -> ${it.exception.message}")
                }
            }
        }

        viewModel.topRatedMovie.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    binding.progressbar.show()
                }
                is DataState.Success -> {
                    binding.progressbar.hide()
                    topRatedMovieAdapter.addItems(it.data)
                }
                is DataState.Error -> {
                    binding.progressbar.hide()
                    Timber.e("Error -> ${it.exception.message}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}