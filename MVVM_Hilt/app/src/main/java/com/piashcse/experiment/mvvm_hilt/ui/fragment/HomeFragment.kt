package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.constants.AppConstants
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentHomeBinding
import com.piashcse.experiment.mvvm_hilt.datasource.local.DataStoreManager
import com.piashcse.experiment.mvvm_hilt.model.user.Address
import com.piashcse.experiment.mvvm_hilt.model.user.Geo
import com.piashcse.experiment.mvvm_hilt.ui.activity.DetailActivity
import com.piashcse.experiment.mvvm_hilt.ui.common.BottomSheetRound
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
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val vm: MainViewModel by viewModels()
    private lateinit var userDataStore: DataStoreManager

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
        userDataStore = DataStoreManager(requireContext())
        binding.apply {
            searchFlow.setOnClickListener {
                findNavController().navigate(R.id.searchWithFlowFragment)
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
                lifecycleScope.launch {
                    userDataStore.storeObjectAsJson(
                        DataStoreManager.USER_NAME_KEY,
                        Geo("1.2", "1.3")
                    )
                    userDataStore.getObjectData<Geo>(DataStoreManager.USER_NAME_KEY).asLiveData()
                        .observe(viewLifecycleOwner, {
                            Timber.e("serialize : $it")
                        })
                }
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