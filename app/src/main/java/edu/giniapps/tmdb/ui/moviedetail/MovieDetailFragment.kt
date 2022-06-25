package edu.giniapps.tmdb.ui.moviedetail

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    /*private var _binding: FragmentMovieDetailBinding? = null
    private val binding
        get() = _binding!!

    private var _movieDetailViewModel: MovieDetailViewModel? = null
    private val movieDetailViewModel
        get() = _movieDetailViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        setupViewModel()

        setupObservers()

        setupReloadButton()

        setupFavoriteButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadMovie()
    }

    private fun setupViewModel() {
        val args: MovieDetailFragmentArgs by navArgs()

        _movieDetailViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

        movieDetailViewModel.setupMovie(args.movie)
    }

    private fun setupObservers() {

        lifecycleScope.launch {
            movieDetailViewModel.favorite.collect {
                binding.fragmentMovieDetailFavoritesButton.background =
                    requireContext().getDrawable(it)
            }
        }

        movieDetailViewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            if (showLoading) {
                binding.movieDetailFragmentLoadingProgressBar.visibility = View.VISIBLE
                binding.movieDetailFragmentMessageTextView.text =
                    getString(edu.giniapps.day23.R.string.home_fragment_loading_text)
                binding.movieDetailFragmentMovieDetailsGroup.visibility = View.GONE
            } else {
                binding.movieDetailFragmentLoadingProgressBar.visibility = View.GONE
            }
        }

        movieDetailViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                binding.movieDetailFragmentMessageTextView.apply {
                    text = it
                    visibility = View.VISIBLE
                }

                binding.movieDetailFragmentReloadImageButton.visibility = View.VISIBLE
            } ?: run {

                binding.movieDetailFragmentMessageTextView.apply {
                    text = null
                    visibility = View.GONE
                }

                binding.movieDetailFragmentReloadImageButton.visibility = View.GONE
            }
        }

        movieDetailViewModel.isReady.observe(viewLifecycleOwner) {
            binding.movieDetailFragmentMovieDetailsGroup.visibility = if (it)
                View.VISIBLE
            else
                View.GONE

            with (binding) {
                Picasso
                    .get()
                    .load(Uri.parse(movieDetailViewModel.thumbnail))
                    .into(binding.fragmentMovieDetailPosterImageView)

                fragmentMovieDetailTitleTextView.text = movieDetailViewModel.title


                val player = YouTubePlayerFragment()

                activity!!.fragmentManager.beginTransaction().
                        replace(R.id.fragment_movie_detail_trailer_player, player).commit()

                player.initialize("key=AIzaSyCVHSVL-j6xpSGgGz3_Tbw_-Yz8gvCC480", object: YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        provider: YouTubePlayer.Provider,
                        youTubePlayer: YouTubePlayer,
                        b: Boolean
                    ) {
                        if (!b) {
                            youTubePlayer.loadVideo(movieDetailViewModel.trailer)
                            youTubePlayer.play()
                        }
                    }

                    override fun onInitializationFailure(
                        p0: YouTubePlayer.Provider?,
                        p1: YouTubeInitializationResult?
                    ) {
                    }
                })

                fragmentMovieDetailYear.text = movieDetailViewModel.year

                fragmentMovieDetailOverviewTextView.text = movieDetailViewModel.overview

                fragmentMovieDetailRatingRatingBar.rating = movieDetailViewModel.voteAverage
            }

        }
    }

    private fun setupReloadButton() {
        binding.movieDetailFragmentReloadImageButton.setOnClickListener {
            loadMovie()
        }
    }

    private fun setupFavoriteButton() {
        binding.fragmentMovieDetailFavoritesButton.setOnClickListener {
            movieDetailViewModel.changeFavorite()
        }
    }

    private fun loadMovie() {
        movieDetailViewModel.loadMovie()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }*/
}