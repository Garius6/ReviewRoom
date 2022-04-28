package com.toohome.android.reviewroom.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.toohome.android.reviewroom.data.ErrorResult
import com.toohome.android.reviewroom.data.PendingResult
import com.toohome.android.reviewroom.data.SuccessResult
import com.toohome.android.reviewroom.databinding.FragmentMovieCollectionListBinding
import com.toohome.android.reviewroom.ui.MovieViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class MovieCollectionFragment : Fragment() {

    private val model: MovieCollectionViewModel by viewModels(factoryProducer = { MovieViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieCollectionListBinding.inflate(inflater, container, false)
        binding.list.adapter = MovieCollectionRecyclerViewAdapter(emptyList())
        model.collections.observe(viewLifecycleOwner) {
            when (it) {
                is SuccessResult -> {
                    binding.list.adapter = MovieCollectionRecyclerViewAdapter(it.data)
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
