package com.piashcse.experiment.mvvm_hilt.ui.nestedviewpagerwithrecycler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentViewPagerWithNestedRecyclerViewBinding
import com.piashcse.experiment.mvvm_hilt.ui.nestedviewpagerwithrecycler.adapter.ParentAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.widget.TextView
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants
import com.piashcse.experiment.mvvm_hilt.data.model.ChildData
import com.piashcse.experiment.mvvm_hilt.data.model.ParentData
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment


@AndroidEntryPoint
class ViewPagerWithNestedRecyclerViewFragment :
    BaseBindingFragment<FragmentViewPagerWithNestedRecyclerViewBinding>() {
    private val parentAdapter: ParentAdapter by lazy {
        ParentAdapter(requireContext())
    }

    override fun init() {
        binding.apply {
            viewPager.apply {
                adapter = parentAdapter
                parentAdapter.addItems(generateData())
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = generateData()[position].title
            }.attach()
            setAllCaps(tabLayout, false)

            viewChange.setOnClickListener {
                if (parentAdapter.getCustomViewType() == AppConstants.ViewType.LIST_VIEW_TYPE) {
                    parentAdapter.changeViewType(AppConstants.ViewType.GRID_VIEW_TYPE)
                } else {
                    parentAdapter.changeViewType(AppConstants.ViewType.LIST_VIEW_TYPE)
                }
            }
        }
    }

    private fun setAllCaps(view: View?, caps: Boolean) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) setAllCaps(view.getChildAt(i), caps)
        } else if (view is TextView) view.isAllCaps = caps
    }

    private fun generateData(): MutableList<ParentData> {
        return mutableListOf(
            ParentData(
                "Tab One", arrayListOf(
                    ChildData("Shirt-Red", 200.0, true, ""),
                    ChildData("Shirt-Blue", 300.0, true, "")
                )
            ),
            ParentData(
                "Tab Tow", arrayListOf(
                    ChildData("Pant-Formal", 300.0, true, ""),
                    ChildData("Pant-Jeans", 400.0, true, "")
                )
            ),
            ParentData(
                "Tab Two", arrayListOf(
                    ChildData("T-Shirt-Grey", 150.0, true, ""),
                    ChildData("T-Shirt-Black", 200.0, true, "")
                )
            ),
            ParentData(
                "Tab Three", arrayListOf(
                    ChildData("T-Shirt-Grey", 150.0, true, ""),
                    ChildData("T-Shirt-Black", 200.0, true, "")
                )
            )
        )
    }
}