package com.piashcse.experiment.mvvm_hilt.ui.pagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.ApiUrls
import com.piashcse.experiment.mvvm_hilt.databinding.AdapterMovieItemPaginationLayoutBinding
import com.piashcse.experiment.mvvm_hilt.utils.loadImage

class MoviePagingAdapter : RecyclerView.Adapter<MoviePagingAdapter.MovieViewHolder>() {
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

    inner class MovieViewHolder(val bind: AdapterMovieItemPaginationLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(item: MovieItem) {
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val bind = AdapterMovieItemPaginationLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(bind)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = items[position]
        holder.bind.image.loadImage(ApiUrls.IMAGE_URL.plus(item.posterPath))
        return holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}