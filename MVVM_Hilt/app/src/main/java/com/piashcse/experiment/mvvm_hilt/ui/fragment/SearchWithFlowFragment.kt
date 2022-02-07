package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentSearchWithFlowBinding
import com.piashcse.experiment.mvvm_hilt.datasource.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.datasource.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.network.Status
import com.piashcse.experiment.mvvm_hilt.ui.adapter.RepositoryAdapter
import com.piashcse.experiment.mvvm_hilt.ui.viewmodel.MainViewModel
import com.piashcse.experiment.mvvm_hilt.utils.errorLog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchWithFlowFragment : Fragment() {
    private var _binding: FragmentSearchWithFlowBinding? = null
    private val binding get() = requireNotNull(_binding) //or _binding!!
    private val vm: MainViewModel by viewModels()
    private val repoAdapter: RepositoryAdapter by lazy {
        RepositoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchWithFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        binding.apply {
            search.doOnTextChanged{ text, _, _, _ ->
                makeFlow(text.toString())
            }
            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = repoAdapter
            }
        }
    }

    private fun apiCall(since: String) = vm.getRepositoryList(since).observe(viewLifecycleOwner) {
        when (it.status) {
            Status.LOADING -> {
                errorLog("Loading..")
            }
            Status.SUCCESS -> {
                errorLog(" Success ... ${it.data}")
                val result = it.data as RepositoriesModel
            }
            Status.ERROR -> {
                errorLog(" Failed ...${it.data}")
            }
        }
    }

    private fun makeFlow(since: String) = vm.makeFlow(since).observe(viewLifecycleOwner) {
        when (it.status) {
            Status.LOADING -> {
                errorLog("Loading..")
            }
            Status.SUCCESS -> {
                val result = it.data as RepoSearchResponse
                repoAdapter.addItems(result.repositories, true)
                errorLog(" Success ... $result")
            }
            Status.ERROR -> {
                errorLog(" Failed ...${it.data}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}