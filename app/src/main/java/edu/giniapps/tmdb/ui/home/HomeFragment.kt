package edu.giniapps.tmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import edu.giniapps.tmdb.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private var _homeViewModel: HomeViewModel? = null
    private val homeViewModel
        get() = _homeViewModel!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupViewModel()

        setupObservers()

        setupRecycler()

        setupRefreshListener()

        return binding.root
    }

    private fun setupViewModel() {
        _homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
    }

    // Todo: Break to functions
    private fun setupObservers() {

        with(binding) {

            homeViewModel.exceptionAction.observe(viewLifecycleOwner) { exceptionAction ->
                exceptionAction?.let {
                    val snackbar =
                        Snackbar.make(root, exceptionAction.messageRes, exceptionAction.length)

                    exceptionAction.actionRes?.let {
                        snackbar.setAction(
                            exceptionAction.actionRes
                        ) {
                            exceptionAction.action()
                        }
                    }

                    snackbar.show()
                }
            }

            homeViewModel.refreshing.observe(viewLifecycleOwner) {
                homeFragmentSwipeRefreshLayout.isRefreshing = it
            }
        }

        homeViewModel.navigate.observe(viewLifecycleOwner) {
            it?.let { movieWithGenre ->

                homeViewModel.acknowledgeNavigation()

                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToMovieDetailFragment(
                        movieWithGenre.movie.id
                    )
                )
            }
        }
    }

    private fun setupRefreshListener() {
        binding.homeFragmentSwipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refreshMovies()
        }
    }

    private fun setupRecycler() {
        LinearSnapHelper().attachToRecyclerView(binding.homeFragmentRecyclerView)

        binding.homeFragmentRecyclerView.apply {
            adapter = homeViewModel.categoriesAdapter
            setRecycledViewPool(homeViewModel.viewPool)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}