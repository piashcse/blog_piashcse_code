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




@AndroidEntryPoint
class ViewPagerWithNestedRecyclerViewFragment : Fragment() {
    private var _binding: FragmentViewPagerWithNestedRecyclerViewBinding? = null
    private val binding get() = requireNotNull(_binding) // or _binding!!
    private val parentAdapter: ParentAdapter by lazy {
        ParentAdapter()
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
            val numberOfTabs = arrayListOf("Tab One", "Tab Two", "Tab Three")
            viewPager.apply {
                adapter = parentAdapter
                parentAdapter.addItems(numberOfTabs)
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = numberOfTabs[position]
            }.attach()
            setAllCaps(tabLayout, false)
        }
    }

    private fun setAllCaps(view: View?, caps: Boolean) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) setAllCaps(view.getChildAt(i), caps)
        } else if (view is TextView) view.isAllCaps = caps
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}