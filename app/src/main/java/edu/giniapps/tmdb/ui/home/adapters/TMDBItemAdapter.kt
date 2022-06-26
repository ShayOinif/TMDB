package edu.giniapps.tmdb.ui.home.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.giniapps.tmdb.R
import edu.giniapps.tmdb.databinding.ViewMovieItemBinding
import edu.giniapps.tmdb.models.MovieWithGenres
import edu.giniapps.tmdb.ui.home.HomeViewModel
import java.lang.ref.WeakReference

class TMDBItemAdapter(private val movieClickCallback: WeakReference<HomeViewModel.MovieClickCallback>) :
    ListAdapter<MovieWithGenres, TMDBItemAdapter.MovieViewHolder>(MovieDiffCallback()) {

    class MovieViewHolder(private val binding: ViewMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieWithGenres) {
            binding.movieItemViewTitleTextView.text = item.movie.name

            item.movie.voteAverage.let { voteAverage ->
                binding.movieItemViewAverageVoteRatingBar.rating = voteAverage
            }

            Picasso
                .get()
                .load(Uri.parse(item.movie.posterPath))
                .error(R.drawable.ic_baseline_broken_image_24)
                .placeholder(R.drawable.loading_animation)
                .into(binding.movieItemViewPosterImageView)

            if (item.genres.isNotEmpty())
                binding.movieItemGenreTextView.text = item.genres[0].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams = ViewGroup.LayoutParams((parent.width * 0.66).toInt(),ViewGroup.LayoutParams.MATCH_PARENT)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            movieClickCallback.get()?.onMovieClicked(getItem(position))
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<MovieWithGenres>() {

        override fun areItemsTheSame(oldItem: MovieWithGenres, newItem: MovieWithGenres) =
            oldItem.movie.id == newItem.movie.id

        override fun areContentsTheSame(oldItem: MovieWithGenres, newItem: MovieWithGenres) =
            oldItem.movie.name == newItem.movie.name &&
                    oldItem.movie.overview == newItem.movie.overview &&
                    oldItem.movie.releaseDate == newItem.movie.releaseDate &&
                    oldItem.movie.voteAverage == newItem.movie.voteAverage
    }
}