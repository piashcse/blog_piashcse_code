package com.piashcse.experiment.mvvm_hilt.ui.datastore

import androidx.core.text.trimmedLength
import androidx.fragment.app.viewModels
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.data.model.user.Address
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentDataStoreBinding
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataStoreFragment : BaseBindingFragment<FragmentDataStoreBinding>() {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    override fun init() {
        initView()
    }

    private fun initView() = with(binding) {
        save.setOnClickListener {
            if ((cityInput.text?.trimmedLength() ?: 0) > 0 && (zipcodeInput.text?.trimmedLength() ?: 0) > 0) {
                dataStoreViewModel.saveUserAddress(
                    Address(
                        cityInput.text.toString(),
                        zipcodeInput.text.toString()
                    )
                )
            } else {
                if ((cityInput.text?.trimmedLength() ?: 0) <= 0)
                    requireActivity().showToast(resources.getString(R.string.enter_you_city_name))
                if ((zipcodeInput.text?.trimmedLength() ?: 0) <= 0)
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
}