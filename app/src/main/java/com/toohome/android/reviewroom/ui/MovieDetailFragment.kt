package com.toohome.android.reviewroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.toohome.android.reviewroom.R
import com.toohome.android.reviewroom.databinding.FragmentDetailMovieBinding
import com.toohome.android.reviewroom.utils.ErrorResult
import com.toohome.android.reviewroom.utils.PendingResult
import com.toohome.android.reviewroom.utils.SuccessResult

private const val TAG = "MovieDetailFragment"

class MovieDetailFragment : Fragment(R.layout.fragment_detail_movie) {
    private val model: MovieDetailViewModel by viewModels(factoryProducer = { MovieViewModelFactory() })
    private lateinit var binding: FragmentDetailMovieBinding
    val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        model.selectedMovieId.value = args.movieId

        hideAll()
        binding.pendingSpinner.isGone = false

        model.movie.observe(this.viewLifecycleOwner) {
            hideAll()
            when (it) {
                is SuccessResult -> {
                    binding.movieName.isGone = false
                    binding.posterView.isGone = false
                    binding.movie = it.data
                    model.downloadInto(it.data.posterUrl, binding.posterView)
                }
                is ErrorResult -> {
                    binding.errorTemplate.isGone = false
                }
                is PendingResult -> {
                    throw NotImplementedError("Pending result never sending ")
                }
            }
        }

        return binding.root
    }

    private fun hideAll() {
        binding.movieName.isGone = true
        binding.posterView.isGone = true
        binding.pendingSpinner.isGone = true
        binding.errorTemplate.isGone = true
    }
}
