package com.piashcse.experiment.mvvm_hilt.ui.nestedviewpagerwithrecycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants
import com.piashcse.experiment.mvvm_hilt.databinding.AdapterParentLayoutBinding
import com.piashcse.experiment.mvvm_hilt.data.model.ChildData
import com.piashcse.experiment.mvvm_hilt.data.model.ParentData
import com.piashcse.experiment.mvvm_hilt.utils.dpToPx

class ParentAdapter(val context: Context) : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {
    private var numberOfTab: MutableList<ParentData> = arrayListOf()
    private lateinit var childAdapter: ChildAdapter
    private var customViewType: Int = AppConstants.ViewType.LIST_VIEW_TYPE
    fun addItems(
        newItems: MutableList<ParentData>?,
        clearPreviousItem: Boolean = false
    ) {
        newItems?.let {
            if (clearPreviousItem) {
                numberOfTab.clear()
                numberOfTab.addAll(newItems)
            } else {
                numberOfTab.addAll(newItems)
            }

            notifyDataSetChanged()
        }
    }

    fun changeViewType(viewType: Int) {
        customViewType = viewType
        notifyDataSetChanged()
    }

    fun getCustomViewType(): Int {
        return childAdapter.getCustomViewType()
    }

    inner class ParentViewHolder(val binding: AdapterParentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(childItems: ArrayList<ChildData>) {
            childAdapter = ChildAdapter()
            binding.childRecycler.apply {
                if (customViewType == AppConstants.ViewType.LIST_VIEW_TYPE) {
                    layoutManager = GridLayoutManager(context, 1)
                    setPadding(10.dpToPx(), 0, 10.dpToPx(), 0)
                } else {
                    layoutManager = GridLayoutManager(context, 2)
                    setPadding(5.dpToPx(), 0, 5.dpToPx(), 0)
                }
                childAdapter.changeViewType(customViewType)
                adapter = childAdapter
            }
            childAdapter.addItems(childItems)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val bind =
            AdapterParentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(numberOfTab[position].childItems)
    }

    override fun getItemCount() = numberOfTab.size
}