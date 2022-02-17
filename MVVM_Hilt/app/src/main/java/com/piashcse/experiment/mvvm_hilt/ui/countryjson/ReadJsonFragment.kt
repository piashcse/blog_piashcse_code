package com.piashcse.experiment.mvvm_hilt.ui.countryjson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentReadJsonBinding
import com.piashcse.experiment.mvvm_hilt.data.model.country.CountryName
import com.piashcse.experiment.mvvm_hilt.ui.countryjson.adapter.CountryAdapter
import com.piashcse.experiment.mvvm_hilt.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadJsonFragment : Fragment() {
    private var _binding: FragmentReadJsonBinding? = null
    private val binding get() = requireNotNull(_binding) // _binding!!
    private val countryAdapter by lazy {
        CountryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReadJsonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
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
            requireContext().showToast("${it.name}")
        }
        countryAdapter.addItems(
            requireActivity().assets.readAssetsFile("country.json").fromPrettyJson<CountryName>()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}