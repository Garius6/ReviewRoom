package com.toohome.android.reviewroom.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.toohome.android.reviewroom.data.Result
import com.toohome.android.reviewroom.databinding.FragmentCollectionMoviePickerBinding
import com.toohome.android.reviewroom.ui.ViewModelFactory

class MoviePickerFragment : Fragment() {
    private lateinit var binding: FragmentCollectionMoviePickerBinding
    private val model: MoviePickerViewModel by activityViewModels(factoryProducer = { ViewModelFactory() })

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
                is Result.Success -> {
                    binding.list.adapter = MoviePickerAdapter(it.data, model)
                }
                is Result.Failure -> {
                    TODO()
                }
                is Result.Pending -> {
                    TODO()
                }
            }
        }
        return binding.root
    }
}
