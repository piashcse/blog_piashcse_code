package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentViewPagerWithNestedRecyclerViewBinding
import com.piashcse.experiment.mvvm_hilt.ui.adapter.ParentAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.widget.TextView
import com.piashcse.experiment.mvvm_hilt.constants.AppConstants
import com.piashcse.experiment.mvvm_hilt.model.ChildData
import com.piashcse.experiment.mvvm_hilt.model.ParentData


@AndroidEntryPoint
class ViewPagerWithNestedRecyclerViewFragment : Fragment() {
    private var _binding: FragmentViewPagerWithNestedRecyclerViewBinding? = null
    private val binding get() = requireNotNull(_binding) // or _binding!!
    private val parentAdapter: ParentAdapter by lazy {
        ParentAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerWithNestedRecyclerViewBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}