package edu.giniapps.tmdb.ui.paging

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.giniapps.tmdb.R
import edu.giniapps.tmdb.databinding.ItemLoadingStateBinding
import edu.giniapps.tmdb.databinding.ViewMovieItemBinding
import edu.giniapps.tmdb.models.response.Movie

class PagingAdapter :
    PagingDataAdapter<Movie, PagingAdapter.PagingMovieViewHolder>(PagingMovieDiffCallback()) {
    class PagingMovieViewHolder(private val binding: ViewMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.movieItemViewTitleTextView.text = item.name

            item.voteAverage.let { voteAverage ->
                binding.movieItemViewAverageVoteRatingBar.rating = voteAverage
            }

            Picasso
                .get()
                .load(Uri.parse(item.posterPath))
                .error(R.drawable.ic_baseline_broken_image_24)
                .placeholder(R.drawable.loading_animation)
                .into(binding.movieItemViewPosterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingMovieViewHolder {
        val binding =
            ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PagingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagingMovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    private class PagingMovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.name == newItem.name &&
                    oldItem.overview == newItem.overview &&
                    oldItem.releaseDate == newItem.releaseDate &&
                    oldItem.voteAverage == newItem.voteAverage
    }
}

class PagingLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PagingLoadStateAdapter.PagingLoadStateViewHolder>() {

    inner class PagingLoadStateViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }

            binding.progressbar.visibility = resolveToVisibility(loadState is LoadState.Loading)
            binding.buttonRetry.visibility = resolveToVisibility(loadState is LoadState.Error)
            binding.textViewError.visibility = resolveToVisibility(loadState is LoadState.Error)
            binding.buttonRetry.setOnClickListener {
                retry()
            }

            binding.progressbar.visibility = View.VISIBLE
        }
    }

    private fun resolveToVisibility(predicate: Boolean) =
        if (predicate)
            View.VISIBLE
        else
            View.GONE

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = PagingLoadStateViewHolder(
        ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}