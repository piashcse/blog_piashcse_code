package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentExpandableRecyclerViewBinding
import com.piashcse.experiment.mvvm_hilt.datasource.model.expandable.Child
import com.piashcse.experiment.mvvm_hilt.datasource.model.expandable.Item
import com.piashcse.experiment.mvvm_hilt.datasource.model.expandable.Parent
import com.piashcse.experiment.mvvm_hilt.ui.adapter.ExpandableCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpandableRecyclerViewFragment : Fragment() {
    private var _binding: FragmentExpandableRecyclerViewBinding? = null
    private val binding get() = requireNotNull(_binding) // or _binding!!
    private lateinit var expandableAdapter: ExpandableCategoryAdapter
    private val expandableItems = ArrayList<Item>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExpandableRecyclerViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        for (i in 1..5) {
            val parent = Parent("Parent sequence $i")
            val childItems = arrayListOf<Child>()
            for (j in 1..5) {
                childItems.add(Child(parent, j, "Child sequence $j"))
            }
            parent.childItems.addAll(childItems)
            expandableItems.add(parent)
        }
        expandableAdapter = ExpandableCategoryAdapter(requireContext(), expandableItems)
        binding.expandableRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = expandableAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}