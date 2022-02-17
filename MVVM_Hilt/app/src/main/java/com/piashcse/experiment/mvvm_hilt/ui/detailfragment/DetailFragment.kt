package com.piashcse.experiment.mvvm_hilt.ui.detailfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants
import com.piashcse.experiment.mvvm_hilt.data.model.user.Address
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentDetailBinding
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val vm: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = requireNotNull(_binding) // or _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}