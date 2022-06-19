package edu.giniapps.day23.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.giniapps.day23.databinding.ViewMovieItemBinding
import edu.giniapps.day23.models.response.Movie
import edu.giniapps.day23.utils.POSTER_BASE_URL
import edu.giniapps.day23.utils.RATING_EVALUATION_DENOMINATOR

class TMDBItemAdapter : ListAdapter<Movie, TMDBItemAdapter.MovieViewHolder>(MovieDiffCallback()) {

    class MovieViewHolder(private val binding: ViewMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.movieItemViewTitleTextView.text = item.name

            item.voteAverage.let { voteAverage ->
                binding.movieItemViewRatingRatingBar.rating =
                    (voteAverage / RATING_EVALUATION_DENOMINATOR).toFloat()
            }

            Picasso
                .get()
                .load(Uri.parse(POSTER_BASE_URL + item.posterPath))
                .into(binding.movieItemViewPosterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.name == newItem.name &&
                    oldItem.overview == newItem.overview &&
                    oldItem.releaseDate == newItem.releaseDate &&
                    oldItem.voteAverage == newItem.voteAverage
    }
}