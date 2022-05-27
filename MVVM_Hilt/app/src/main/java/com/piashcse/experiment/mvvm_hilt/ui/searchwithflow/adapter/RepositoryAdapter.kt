package com.piashcse.experiment.mvvm_hilt.ui.searchwithflow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.mvvm_hilt.databinding.RepositoryItemBinding
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    private val items: MutableList<MovieItem> = arrayListOf()
    var onItemClick: ((MovieItem) -> Unit)? = null

    fun addItems(newItems: List<MovieItem>?, clearPreviousItem: Boolean = false) {
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

    inner class RepositoryViewHolder(val bind: RepositoryItemBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(item: MovieItem) {
            bind.executePendingBindings()
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val bind = RepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val item = items[position]
        holder.bind.name.text = item.title
        return holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}