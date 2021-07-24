package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.constants.AppConstants
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentHomeBinding
import com.piashcse.experiment.mvvm_hilt.datasource.local.DataStoreManager
import com.piashcse.experiment.mvvm_hilt.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.model.user.Address
import com.piashcse.experiment.mvvm_hilt.model.user.Geo
import com.piashcse.experiment.mvvm_hilt.model.user.User
import com.piashcse.experiment.mvvm_hilt.network.Status
import com.piashcse.experiment.mvvm_hilt.ui.activity.DetailActivity
import com.piashcse.experiment.mvvm_hilt.ui.adapter.RepositoryAdapter
import com.piashcse.experiment.mvvm_hilt.ui.viewmodel.MainViewModel
import com.piashcse.experiment.mvvm_hilt.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
    private lateinit var userDataStore: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()

        return binding.root
    }

    private fun initView() {
        userDataStore = DataStoreManager(requireContext())
        repoAdapter = RepositoryAdapter()
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        binding.search.doOnTextChanged { text, start, before, count ->
            makeFlow(text.toString())
        }
        binding.localDb.setOnClickListener {
            it?.findNavController()?.navigate(R.id.roomDBFragment)
        }
        binding.detailFragment.setOnClickListener {
            it?.findNavController()
                ?.navigate(
                    R.id.detailFragment,
                    bundleOf(AppConstants.DataTask.DATA to Address("Dhaka", "1205"))
                )
        }
        binding.detailActivity.setOnClickListener {
            requireActivity().openActivity<DetailActivity>(
                AppConstants.DataTask.ADDRESS to Address(
                    "Dhaka",
                    "1205"
                )
            )
        }

        binding.detailActivityResult.setOnClickListener {
            resultContract.launch(requireActivity().openActivityResult<DetailActivity>())
        }

        binding.dataStore.setOnClickListener {
            lifecycleScope.launch {
                userDataStore.storeObjectData(DataStoreManager.USER_NAME_KEY, Geo("1.2", "1.3"))
                userDataStore.getObjectData<Geo>(DataStoreManager.USER_NAME_KEY).asLiveData()
                    .observe(viewLifecycleOwner, {
                        Timber.e("serialize : $it")
                    })
            }
        }

        binding.imagePicker.setOnClickListener {
            findNavController().navigate(R.id.imagePickerFragment)
        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getParcelable<Address>("bundleKey")
            requireContext().showToast("$result")

            // Do something with the result
        }


    }

    private val resultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                requireContext().showToast("result received")
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