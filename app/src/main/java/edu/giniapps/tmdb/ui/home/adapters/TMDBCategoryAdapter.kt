package edu.giniapps.tmdb.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.giniapps.tmdb.R
import edu.giniapps.tmdb.databinding.ViewMovieListItemBinding
import edu.giniapps.tmdb.models.MoviesCategoryItem
import java.lang.ref.WeakReference

class TMDBCategoryAdapter(private val wrViewPool: WeakReference<RecyclerView.RecycledViewPool>) :
    ListAdapter<MoviesCategoryItem, TMDBCategoryAdapter.MovieViewHolder>(MovieDiffCallback()) {

    class MovieViewHolder(private val binding: ViewMovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesCategoryItem) {
            binding.movieListItemViewCategoryTextView.text =
                binding.root.context.getString(item.categoryNameStringRes)

            LinearSnapHelper().attachToRecyclerView(binding.movieListItemViewRecyclerView)

            binding.movieListItemViewRecyclerView.apply {
                adapter = item.adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ViewMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (parent.height * 0.66).toInt()
        )

        return MovieViewHolder(
            binding
        )
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.findViewById<RecyclerView>(R.id.movie_list_item_view_recycler_view)
            .setRecycledViewPool(wrViewPool.get())
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<MoviesCategoryItem>() {

        override fun areItemsTheSame(oldItem: MoviesCategoryItem, newItem: MoviesCategoryItem) =
            oldItem.categoryNameStringRes == newItem.categoryNameStringRes

        override fun areContentsTheSame(oldItem: MoviesCategoryItem, newItem: MoviesCategoryItem) =
            oldItem.adapter.currentList == newItem.adapter.currentList
    }
}