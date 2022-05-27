package com.piashcse.experiment.mvvm_hilt.ui.searchwithflow

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentSearchWithFlowBinding
import com.piashcse.experiment.mvvm_hilt.ui.searchwithflow.adapter.RepositoryAdapter
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.errorLog
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import com.piashcse.experiment.mvvm_hilt.utils.show
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

    private fun makeFlow(since: String) =
        searchFlowViewModel.makeFlow(since).observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    binding.progressBar.show()
                    errorLog("Loading..")
                }
                is DataState.Success -> {
                    binding.progressBar.hide()
                    val result = it.data
                    repoAdapter.addItems(result.results, true)
                    errorLog(" Success ... $result")
                }
                is DataState.Error -> {
                    binding.progressBar.hide()
                    errorLog(" Failed ...${it}")
                }
            }
        }
}