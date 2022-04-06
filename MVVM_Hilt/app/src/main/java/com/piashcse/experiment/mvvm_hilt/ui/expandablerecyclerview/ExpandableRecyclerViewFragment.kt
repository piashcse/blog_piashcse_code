package com.piashcse.experiment.mvvm_hilt.ui.expandablerecyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentExpandableRecyclerViewBinding
import com.piashcse.experiment.mvvm_hilt.data.model.expandable.Child
import com.piashcse.experiment.mvvm_hilt.data.model.expandable.Item
import com.piashcse.experiment.mvvm_hilt.data.model.expandable.Parent
import com.piashcse.experiment.mvvm_hilt.ui.countryjson.adapter.ExpandableCategoryAdapter
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpandableRecyclerViewFragment : BaseBindingFragment<FragmentExpandableRecyclerViewBinding>() {
    private lateinit var expandableAdapter: ExpandableCategoryAdapter
    private val expandableItems = ArrayList<Item>()

    override fun init() {
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
}