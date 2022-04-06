package com.piashcse.experiment.mvvm_hilt.ui.detailfragment

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants
import com.piashcse.experiment.mvvm_hilt.data.model.user.Address
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentDetailBinding
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : BaseBindingFragment<FragmentDetailBinding>() {
    private val vm: DetailViewModel by viewModels()

    override fun init() {
        val data = arguments?.getParcelable<Address>(AppConstants.DataTask.DATA)
        Timber.e("data received : $data")
        binding.detail.setOnClickListener {
            setFragmentResult("requestKey", bundleOf("bundleKey" to Address("Dhaka", "1205")))
            it?.findNavController()?.navigateUp()
        }
        binding.apiCall.setOnClickListener {
            vm.githubRepositories("")
        }
        vm.repositoryResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    Timber.e("Loading..")
                }
                is DataState.Success -> {
                    Timber.e("Success..")
                }
                is DataState.Error -> {
                    Timber.e("ERRor..")
                }
            }
        }
    }
}