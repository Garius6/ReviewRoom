package com.toohome.android.reviewroom.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.toohome.android.reviewroom.databinding.FragmentCollectionListBinding
import com.toohome.android.reviewroom.ui.ViewModelFactory

/**
 * A fragment representing a list of Items.
 */
private const val TAG = "MovieCollectionFragment"

class MovieCollectionFragment : Fragment() {
    private val args: MovieCollectionFragmentArgs by navArgs()
    private val model: MovieCollectionViewModel by viewModels(factoryProducer = { ViewModelFactory() })
    private lateinit var binding: FragmentCollectionListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionListBinding.inflate(inflater, container, false)
        binding.list.adapter = MovieCollectionRecyclerViewAdapter(emptyList())
        model.getCollections(args.userId)
        binding.addCollectionButton.setOnClickListener {
            val action =
                MovieCollectionFragmentDirections.actionCollectionsFragmentToCollectionCreateFragment2()
            it.findNavController().navigate(action)
        }
        model.collectionsUI.observe(viewLifecycleOwner) { uiState ->
            binding.addCollectionButton.isVisible = uiState.isUsersCollections
            uiState.error?.let {
                binding.list.isVisible = false
                binding.errorTemplate.isVisible = true
            }
            uiState.collections?.let {
                binding.list.isVisible = true
                binding.errorTemplate.isVisible = false
                binding.list.adapter = MovieCollectionRecyclerViewAdapter(it)
            }
        }

        return binding.root
    }
}
