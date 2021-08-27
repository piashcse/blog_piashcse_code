package com.piashcse.experiment.mvvm_hilt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.mvvm_hilt.databinding.RepositoryItemBinding
import com.piashcse.experiment.mvvm_hilt.model.Repository

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    private val items: MutableList<Repository> = arrayListOf()
    var onItemClick: ((Repository) -> Unit)? = null

    fun addItems(newItems: List<Repository>?, clearPreviousItem: Boolean = false) {
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
        fun bind(item: Repository) {
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
        holder.bind.name.text = item.name
        return holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}