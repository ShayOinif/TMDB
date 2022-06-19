package edu.giniapps.day23.ui.home

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import edu.giniapps.day23.R
import edu.giniapps.day23.databinding.FragmentHomeBinding
import edu.giniapps.day23.networking.NetworkStatusChecker
import edu.giniapps.day23.utils.RECYCLER_VIEW_COLUMNS

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private var _homeViewModel: HomeViewModel? = null
    private val homeViewModel
        get() = _homeViewModel!!

    private val networkStatusChecker = NetworkStatusChecker(
        getSystemService(
            requireContext(),
            ConnectivityManager::class.java
        ) as ConnectivityManager
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupObservers()

        setupReloadButton()

        setupRecycler()

        loadMovies()

        return root
    }

    private fun setupObservers() {
        homeViewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            if (showLoading) {
                binding.homeFragmentLoadingProgressBar.visibility = View.VISIBLE
                binding.homeFragmentRecyclerView.visibility = View.GONE
                binding.homeFragmentMessageTextView.text =
                    getString(R.string.home_fragment_loading_text)
            } else {
                binding.homeFragmentLoadingProgressBar.visibility = View.GONE
                binding.homeFragmentRecyclerView.visibility = View.VISIBLE
            }
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                binding.homeFragmentMessageTextView.apply {
                    text = it
                    visibility = View.VISIBLE
                }

                binding.homeFragmentReloadImageButton.visibility = View.VISIBLE
            } ?: run {

                binding.homeFragmentMessageTextView.apply {
                    text = null
                    visibility = View.GONE
                }

                binding.homeFragmentReloadImageButton.visibility = View.GONE
            }
        }
    }

    private fun setupReloadButton() {
        binding.homeFragmentReloadImageButton.setOnClickListener {
            loadMovies()
        }
    }

    private fun setupRecycler() {
        binding.homeFragmentRecyclerView.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                RECYCLER_VIEW_COLUMNS
            )

            adapter = homeViewModel.adapter
        }
    }

    private fun loadMovies() {
        if (!networkStatusChecker.performIfConnectedToInternet { homeViewModel.loadMovies() }) {
            binding.homeFragmentMessageTextView.text = "Not connected to the internet"
        }

    }

    override fun onResume() {
        super.onResume()

        homeViewModel.loadMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}