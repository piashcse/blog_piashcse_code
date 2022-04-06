package com.piashcse.experiment.mvvm_hilt.ui.searchwithflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentSearchWithFlowBinding
import com.piashcse.experiment.mvvm_hilt.data.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.data.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.utils.network.Status
import com.piashcse.experiment.mvvm_hilt.ui.searchwithflow.adapter.RepositoryAdapter
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.errorLog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchWithFlowFragment : BaseBindingFragment<FragmentSearchWithFlowBinding>() {
    private val searchFlowViewModel: SearchFlowViewModel by viewModels()
    private val repoAdapter: RepositoryAdapter by lazy {
        RepositoryAdapter()
    }

    override fun init() {
        binding.apply {
            search.doOnTextChanged { text, _, _, _ ->
                makeFlow(text.toString())
            }
            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = repoAdapter
            }
        }
    }

    private fun apiCall(since: String) =
        searchFlowViewModel.getRepositoryList(since).observe(viewLifecycleOwner) {
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

    private fun makeFlow(since: String) =
        searchFlowViewModel.makeFlow(since).observe(viewLifecycleOwner) {
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
}