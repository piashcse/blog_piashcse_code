package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentReadJsonBinding
import com.piashcse.experiment.mvvm_hilt.model.country.CountryName
import com.piashcse.experiment.mvvm_hilt.ui.adapter.CountryAdapter
import com.piashcse.experiment.mvvm_hilt.utils.fromPrettyJson
import com.piashcse.experiment.mvvm_hilt.utils.readAssetsFile
import com.piashcse.experiment.mvvm_hilt.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [ReadJsonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ReadJsonFragment : Fragment() {
    private lateinit var binding: FragmentReadJsonBinding
    private val countryAdapter by lazy {
        CountryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReadJsonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.countryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }
        countryAdapter.onItemClick = {
            requireContext().showToast("${it.name}")
        }
        countryAdapter.addItems(
            requireActivity().assets.readAssetsFile("country.json").fromPrettyJson<CountryName>()
        )
    }
}