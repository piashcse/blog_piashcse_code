package com.piashcse.experiment.mvvm_hilt.ui.nestedviewpagerwithrecycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants
import com.piashcse.experiment.mvvm_hilt.databinding.AdapterChildHorizontalLayoutBinding
import com.piashcse.experiment.mvvm_hilt.databinding.AdapterChildVerticalLayoutBinding
import com.piashcse.experiment.mvvm_hilt.data.model.ChildData

class ChildAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: MutableList<ChildData> = arrayListOf()
    private var customVIewType: Int = AppConstants.ViewType.LIST_VIEW_TYPE

    fun addItems(newItems: MutableList<ChildData>?, clearPreviousItem: Boolean = false) {
        newItems?.let {
            if (clearPreviousItem) {
                items.clear()
                items.addAll(newItems)
            } else {
                items.addAll(newItems)
            }
            notifyDataSetChanged()
        }
    }

    fun changeViewType(viewType: Int) {
        customVIewType = viewType
    }

    fun getCustomViewType(): Int {
        return customVIewType
    }

    inner class VerticalViewHolder(val binding: AdapterChildVerticalLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(childItem: ChildData) {
            binding.apply {
                item = childItem
                executePendingBindings()
            }
        }
    }

    inner class HorizontalViewHolder(val binding: AdapterChildHorizontalLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(childItem: ChildData) {
            binding.apply {
                item = childItem
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (customVIewType == AppConstants.ViewType.LIST_VIEW_TYPE) {
            val binding = AdapterChildHorizontalLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            HorizontalViewHolder(binding)
        } else {
            val binding = AdapterChildVerticalLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            VerticalViewHolder(binding)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VerticalViewHolder) {
            holder.bind(items[position])
        } else if (holder is HorizontalViewHolder) {
            holder.bind(items[position])
        }
    }

    override fun getItemCount() = items.size

}