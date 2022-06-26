package edu.giniapps.tmdb.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import dagger.hilt.android.AndroidEntryPoint
import edu.giniapps.tmdb.databinding.FragmentPagingBinding

@AndroidEntryPoint
class PagingFragment : Fragment() {
    private var _binding: FragmentPagingBinding? = null
    private val binding
        get() = _binding!!

    private var _pagingViewModel: PagingViewModel? = null
    private val pagingViewModel
        get() = _pagingViewModel!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPagingBinding.inflate(inflater, container, false)

        setupViewModel()

        setupRecycler()

        pagingViewModel.start()

        return binding.root
    }

    private fun setupViewModel() {
        _pagingViewModel =
            ViewModelProvider(this)[PagingViewModel::class.java]
    }

    private fun setupRecycler() {
        binding.pagingFragmentRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = pagingViewModel.adapter

            addItemDecoration(DividerItemDecoration(
                context,
                OrientationHelper.HORIZONTAL
            ))
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    OrientationHelper.VERTICAL
                ))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}