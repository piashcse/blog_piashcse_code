package com.piashcse.experiment.viewpager2withrvadapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.viewpager2withrvadapter.databinding.ImageSliderItemLayoutBinding

class ImageSliderAdapter: RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {
    private var items = ArrayList<String>()
    private var onItemClick: ((String) -> Unit)? = null

    fun addItem(newItems: List<String>?) {
        newItems?.let {
            items.addAll(newItems)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageSliderItemLayoutBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = items[position]
        return holder.bind(image)
    }

    inner class ImageViewHolder(private val bind: ImageSliderItemLayoutBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(item: String) {
            bind.item = item
            bind.executePendingBindings()
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}