package com.toohome.android.reviewroom.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.toohome.android.reviewroom.databinding.FragmentCollectionCreateBinding
import com.toohome.android.reviewroom.ui.MovieListAdapter
import com.toohome.android.reviewroom.ui.MovieViewModelFactory

class CollectionCreateFragment : Fragment() {

    private lateinit var binding: FragmentCollectionCreateBinding
    private val model: MoviePickerViewModel by activityViewModels(factoryProducer = { MovieViewModelFactory() })
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCollectionCreateBinding.inflate(inflater, container, false)
        binding.list.adapter = MovieListAdapter(emptyList(), model)
        binding.list.layoutManager = GridLayoutManager(context, 3)
        model.selectedMovies.observe(viewLifecycleOwner) {
            binding.list.adapter = MovieListAdapter(it, model)
        }
        binding.addMovieButton.setOnClickListener {
            val action =
                CollectionCreateFragmentDirections.actionCollectionCreateFragment2ToMoviePickerFragment()
            it.findNavController().navigate(action)
        }
        return binding.root
    }
}
