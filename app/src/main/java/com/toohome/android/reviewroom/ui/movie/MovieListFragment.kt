package com.toohome.android.reviewroom.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.toohome.android.reviewroom.data.ErrorResult
import com.toohome.android.reviewroom.data.PendingResult
import com.toohome.android.reviewroom.data.SuccessResult
import com.toohome.android.reviewroom.databinding.ListMovieBinding
import com.toohome.android.reviewroom.ui.ViewModelFactory

private const val TAG = "MovieListFragment"

class MovieListFragment : Fragment() {
    private lateinit var binding: ListMovieBinding
    private val model: MovieListViewModel by viewModels(factoryProducer = { ViewModelFactory() })
    private val adapter by lazy { MovieListAdapter(emptyList(), model) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListMovieBinding.inflate(inflater, container, false)
        binding.rcMovieList.adapter = adapter
        binding.rcMovieList.layoutManager = GridLayoutManager(this.context, 3)

        hideAll()
        model.movies.observe(viewLifecycleOwner) {
            hideAll()
            when (it) {
                is SuccessResult -> {
                    binding.rcMovieList.isVisible = true
                    binding.rcMovieList.adapter = MovieListAdapter(it.data, model)
                }
                is ErrorResult -> {
                    binding.errorTemplate.isVisible = true
                }
                is PendingResult -> {
                    TODO()
                }
            }
        }
        return binding.root
    }

    private fun hideAll() {
        binding.rcMovieList.isVisible = false
        binding.errorTemplate.isVisible = false
    }
}
