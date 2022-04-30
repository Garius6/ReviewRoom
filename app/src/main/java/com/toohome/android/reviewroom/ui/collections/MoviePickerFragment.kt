package com.toohome.android.reviewroom.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.toohome.android.reviewroom.data.ErrorResult
import com.toohome.android.reviewroom.data.PendingResult
import com.toohome.android.reviewroom.data.SuccessResult
import com.toohome.android.reviewroom.databinding.FragmentCollectionMoviePickerBinding
import com.toohome.android.reviewroom.ui.MovieViewModelFactory

class MoviePickerFragment : Fragment() {
    private lateinit var binding: FragmentCollectionMoviePickerBinding
    private val model: MoviePickerViewModel by activityViewModels(factoryProducer = { MovieViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionMoviePickerBinding.inflate(inflater, container, false)
        binding.list.adapter = MoviePickerAdapter(emptyList(), model)
        binding.list.layoutManager = GridLayoutManager(context, 3)
        model.movies.observe(viewLifecycleOwner) {
            when (it) {
                is SuccessResult -> {
                    binding.list.adapter = MoviePickerAdapter(it.data, model)
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
