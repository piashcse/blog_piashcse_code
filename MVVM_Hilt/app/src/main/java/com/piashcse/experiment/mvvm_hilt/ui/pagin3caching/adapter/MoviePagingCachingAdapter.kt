package com.piashcse.experiment.mvvm_hilt.ui.pagin3caching.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.ApiUrls
import com.piashcse.experiment.mvvm_hilt.databinding.AdapterMovieItemPaging3LayoutBinding
import com.piashcse.experiment.mvvm_hilt.utils.loadImage

class MoviePagingCachingAdapter :
    PagingDataAdapter<MovieItem, MoviePagingCachingAdapter.MovieViewHolder>(DataDifferentiator) {
    var onItemClick: ((MovieItem) -> Unit)? = null

    inner class MovieViewHolder(val bind: AdapterMovieItemPaging3LayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(item: MovieItem) {
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind.image.loadImage(ApiUrls.IMAGE_URL.plus(getItem(position)?.posterPath))
        getItem(position)?.let { holder.bind(it) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val bind = AdapterMovieItemPaging3LayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(bind)
    }

    object DataDifferentiator : DiffUtil.ItemCallback<MovieItem>() {

        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem == newItem
        }
    }

}