package com.piashcse.experiment.mvvm_hilt.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentHomeBinding
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.DataStoreManager
import com.piashcse.experiment.mvvm_hilt.data.model.user.Address
import com.piashcse.experiment.mvvm_hilt.data.model.user.Geo
import com.piashcse.experiment.mvvm_hilt.ui.activity.DetailActivity
import com.piashcse.experiment.mvvm_hilt.ui.commonui.BottomSheetRound
import com.piashcse.experiment.mvvm_hilt.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            searchFlow.setOnClickListener {
                findNavController().navigate(R.id.searchWithFlowFragment)
            }
            parallelApiCall.setOnClickListener {
                findNavController().navigate(R.id.parallelApiCallFragment)
            }
            paging3.setOnClickListener {
                findNavController().navigate(R.id.paging3Fragment)
            }
            localDb.setOnClickListener {
                findNavController().navigate(R.id.roomDBFragment)
            }
            detailFragment.setOnClickListener {
                findNavController().navigate(
                    R.id.detailFragment,
                    bundleOf(AppConstants.DataTask.DATA to Address("Dhaka", "1205"))
                )
            }
            detailActivity.setOnClickListener {
                requireActivity().openActivity<DetailActivity>(
                    AppConstants.DataTask.ADDRESS to Address(
                        "Dhaka",
                        "1205"
                    )
                )
            }

            detailActivityResult.setOnClickListener {
                resultContract.launch(requireActivity().openActivityResult<DetailActivity>())
            }

            dataStore.setOnClickListener {
                findNavController().navigate(R.id.dataStoreFragment)
            }

            imagePicker.setOnClickListener {
                findNavController().navigate(R.id.imagePickerFragment)
            }

            readJson.setOnClickListener {
                findNavController().navigate(R.id.readJsonFragment)
            }

            viewPagerWithRecycler.setOnClickListener {
                findNavController().navigate(R.id.viewPagerWithNestedRecyclerViewFragment)
            }
            expandableRecyclerveiw.setOnClickListener {
                findNavController().navigate(R.id.expandableRecyclerViewFragment)
            }
            bottomNavigation.setOnClickListener {
                findNavController().navigate(R.id.bottomNavigationFragment)
            }

            googleLogin.setOnClickListener {
                findNavController().navigate(R.id.googleLoginFragment)
            }

            bottomSheet.setOnClickListener {
                BottomSheetRound().show(childFragmentManager, AppConstants.Dialog.DIALOG_TAG)
            }
            gpsLocation.setOnClickListener {
                findNavController().navigate(R.id.gpsLocationFragment)
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}