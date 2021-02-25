package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentHomeBinding
import com.piashcse.experiment.mvvm_hilt.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.network.Status
import com.piashcse.experiment.mvvm_hilt.ui.activity.DetailActivity
import com.piashcse.experiment.mvvm_hilt.ui.adapter.RepositoryAdapter
import com.piashcse.experiment.mvvm_hilt.ui.viewmodel.MainViewModel
import com.piashcse.experiment.mvvm_hilt.utils.errorLog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val vm: MainViewModel by viewModels()
    private lateinit var repoAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        Timber.e("YO YO")
        initView()

        return binding.root
    }

    private fun initView() {
        repoAdapter = RepositoryAdapter()
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        binding.search.doOnTextChanged { text, start, before, count ->
            makeFlow(text.toString())
        }
        binding.button.setOnClickListener {
            it?.findNavController()?.navigate(R.id.detailFragment)
        }
        binding.detailActivity.setOnClickListener {
            resultContract.launch(Intent(activity, DetailActivity::class.java))
        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getString("bundleKey")
            Toast.makeText(context, "$result", Toast.LENGTH_SHORT).show()

            // Do something with the result
        }

    }

    private val resultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "result received", Toast.LENGTH_SHORT).show()
            }
        }

    private fun apiCall(since: String) = vm.getRepositoryList(since).observe(viewLifecycleOwner, {
        when (it.status) {
            Status.LOADING -> {
                errorLog("Loading..")
            }
            Status.SUCCESS -> {
                errorLog(" Success ... ${it.data}")
                val result = it.data as RepositoriesModel
                repoAdapter.addItems(result)
            }
            Status.ERROR -> {
                errorLog(" Failed ...${it.data}")
            }
        }
    })

    private fun makeFlow(since: String) = vm.makeFlow(since).observe(viewLifecycleOwner, {
        when (it.status) {
            Status.LOADING -> {
                errorLog("Loading..")
            }
            Status.SUCCESS -> {
                val result = it.data as RepoSearchResponse
                errorLog(" Success ... $result")
            }
            Status.ERROR -> {
                errorLog(" Failed ...${it.data}")
            }
        }
    })
}