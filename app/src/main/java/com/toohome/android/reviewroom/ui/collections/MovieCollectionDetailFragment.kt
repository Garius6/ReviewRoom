package com.toohome.android.reviewroom.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.toohome.android.reviewroom.data.ErrorResult
import com.toohome.android.reviewroom.data.PendingResult
import com.toohome.android.reviewroom.data.SuccessResult
import com.toohome.android.reviewroom.databinding.FragmentCollectionDetailListBinding
import com.toohome.android.reviewroom.ui.ViewModelFactory
import com.toohome.android.reviewroom.ui.movie.MovieListAdapter

class MovieCollectionDetailFragment : Fragment() {
    private val model: MovieCollectionDetailViewModel by viewModels(factoryProducer = { ViewModelFactory() })
    private val args: MovieCollectionDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCollectionDetailListBinding.inflate(inflater, container, false)
        binding.collectionDetailMovieList.layoutManager = GridLayoutManager(this.context, 3)
        binding.collectionDetailMovieList.adapter = MovieListAdapter(emptyList(), model)

        model.getCollection(args.collectionId)
        model.collection.observe(this.viewLifecycleOwner) {
            when (it) {
                is SuccessResult -> {
                    binding.collectionDetailMovieList.adapter =
                        MovieListAdapter(it.data.movies, model)
                }
                is ErrorResult -> {
                    TODO()
                }
                is PendingResult -> {
                    TODO()
                }
            }
        }
        return binding.root
    }
}
