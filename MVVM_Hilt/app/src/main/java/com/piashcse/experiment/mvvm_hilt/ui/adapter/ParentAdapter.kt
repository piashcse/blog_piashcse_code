package com.piashcse.experiment.mvvm_hilt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.mvvm_hilt.databinding.AdapterParentLayoutBinding

class ParentAdapter : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {
    private var numberOfTab: ArrayList<String> = arrayListOf()

    fun addItems(newItems: List<String>?, clearPreviousItem: Boolean = false) {
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

    class ParentViewHolder(val binding: AdapterParentLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val bind = AdapterParentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(numberOfTab[position])
    }

    override fun getItemCount() = numberOfTab.size
}