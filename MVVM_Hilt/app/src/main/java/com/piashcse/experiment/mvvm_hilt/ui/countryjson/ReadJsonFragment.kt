package com.piashcse.experiment.mvvm_hilt.ui.countryjson

import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentReadJsonBinding
import com.piashcse.experiment.mvvm_hilt.data.model.country.CountryName
import com.piashcse.experiment.mvvm_hilt.ui.countryjson.adapter.CountryAdapter
import com.piashcse.experiment.mvvm_hilt.utils.*
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadJsonFragment : BaseBindingFragment<FragmentReadJsonBinding>() {
    private val countryAdapter by lazy {
        CountryAdapter()
    }

    override fun init() {
        binding.apply {
            countryList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = countryAdapter
            }
            countryFilter.doOnTextChanged { text, _, _, _ ->
                countryAdapter.filter(text.toString())
                text?.let {
                    if (it.isNotEmpty()) cancel.show() else cancel.hide()
                }
            }
            cancel.setOnClickListener {
                countryFilter.text?.clear()
            }
        }
        countryAdapter.onItemClick = {
            requireContext().showToast(it.name)
        }
        countryAdapter.addItems(
            requireActivity().assets.readAssetsFile("country.json").fromPrettyJson<CountryName>()
        )
    }
}