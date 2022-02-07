package com.piashcse.experiment.mvvm_hilt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.mvvm_hilt.databinding.AdapterCountryItemLayoutBinding
import com.piashcse.experiment.mvvm_hilt.datasource.model.country.CountryNameItem
import kotlin.collections.ArrayList

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private val items: ArrayList<CountryNameItem> = arrayListOf()
    var onItemClick: ((CountryNameItem) -> Unit)? = null
    private val itemsCopies: ArrayList<CountryNameItem> = arrayListOf()

    fun addItems(newItems: List<CountryNameItem>) {
        items.addAll(newItems)
        itemsCopies.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryAdapter.CountryViewHolder {
        val binding =
            AdapterCountryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CountryViewHolder(binding)
    }

    inner class CountryViewHolder(val binding: AdapterCountryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CountryNameItem) {
            binding.country = item
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    override fun onBindViewHolder(holder: CountryAdapter.CountryViewHolder, position: Int) {
        val item = items[position]
        return holder.bind(item)
    }

    override fun getItemCount() = items.size

    fun filter(sequence: CharSequence?) {
        val temp: ArrayList<CountryNameItem> = arrayListOf()
        if (sequence.isNullOrEmpty().not()) {
            for (s in itemsCopies) {
                if (s.name.lowercase().contains(sequence!!)) {
                    temp.add(s)
                } else {
                    for (code in s.callingCodes) {
                        if (code.contains(sequence)) {
                            temp.add(s)
                        }
                    }
                }
            }
        } else {
            temp.addAll(itemsCopies)
        }
        items.clear()
        items.addAll(temp)
        notifyDataSetChanged()
        temp.clear()
    }
}