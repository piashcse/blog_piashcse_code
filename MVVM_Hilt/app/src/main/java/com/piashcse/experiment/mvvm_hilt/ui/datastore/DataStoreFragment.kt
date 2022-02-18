package com.piashcse.experiment.mvvm_hilt.ui.datastore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.trimmedLength
import androidx.fragment.app.viewModels
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.data.model.user.Address
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentDataStoreBinding
import com.piashcse.experiment.mvvm_hilt.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataStoreFragment : Fragment() {
    private var _binding: FragmentDataStoreBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDataStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        save.setOnClickListener {
            if (cityInput.text?.trimmedLength() ?: 0 > 0 && zipcodeInput.text?.trimmedLength() ?: 0 > 0) {
                dataStoreViewModel.saveUserAddress(
                    Address(
                        cityInput.text.toString(),
                        zipcodeInput.text.toString()
                    )
                )
            } else {
                if (cityInput.text?.trimmedLength() ?: 0 <= 0)
                    requireActivity().showToast(resources.getString(R.string.enter_you_city_name))
                if (zipcodeInput.text?.trimmedLength() ?: 0 <= 0)
                    requireActivity().showToast(resources.getString(R.string.enter_your_city_zip_code))
            }
        }
        showData.setOnClickListener {
            dataStoreViewModel.getUserAddress().observe(viewLifecycleOwner) {
                it?.let {
                    cityText.text = it.city
                    zipcodeText.text = it.zipcode
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}