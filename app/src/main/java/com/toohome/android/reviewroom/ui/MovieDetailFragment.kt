package com.toohome.android.reviewroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.toohome.android.reviewroom.R
import com.toohome.android.reviewroom.databinding.FragmentDetailMovieBinding
import com.toohome.android.reviewroom.utils.ErrorResult
import com.toohome.android.reviewroom.utils.SuccessResult

private const val TAG = "MovieDetailFragment"

class MovieDetailFragment : Fragment(R.layout.fragment_detail_movie) {
    private val model: MovieDetailViewModel by viewModels(factoryProducer = { MovieViewModelFactory() })
    private lateinit var binding: FragmentDetailMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        model.movie.observe(this.viewLifecycleOwner) {
            when (it) {
                is SuccessResult -> binding.movie = it.data
                is ErrorResult -> throw Exception(it.error.message)
            }
        }

        model.selectedMovieId.value = 0L
        return binding.root
    }
}
