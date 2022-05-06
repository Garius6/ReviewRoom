package com.toohome.android.reviewroom.ui.collections

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
        binding = FragmentCollectionCreateBinding.inflate(inflater, container, false)
        binding.collectionName.setText(model.collectionName.value ?: "")

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

        binding.createCollectionButton.setOnClickListener {
            model.createCollection()
            val action =
                CollectionCreateFragmentDirections.actionCollectionCreateFragment2ToCollectionsFragment(
                    model.userId!!
                )
            it.findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                model.collectionNameChanged(
                    binding.collectionName.text.toString()
                )
            }
        }
        binding.collectionName.addTextChangedListener(afterTextChangedListener)
        binding.collectionName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                model.collectionNameChanged(
                    binding.collectionName.text.toString()
                )
            }
            true
        }
    }
}
