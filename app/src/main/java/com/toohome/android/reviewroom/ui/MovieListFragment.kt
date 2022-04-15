package com.toohome.android.reviewroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toohome.android.reviewroom.R
import com.toohome.android.reviewroom.databinding.ListItemMovieBinding
import com.toohome.android.reviewroom.databinding.ListMovieBinding
import com.toohome.android.reviewroom.model.Movie
import com.toohome.android.reviewroom.utils.ErrorResult
import com.toohome.android.reviewroom.utils.PendingResult
import com.toohome.android.reviewroom.utils.SuccessResult

class MovieListFragment : Fragment() {
    private lateinit var binding: ListMovieBinding
    private val adapter = MovieAdapter(emptyList())
    private val model: MovieListViewModel by viewModels(factoryProducer = { MovieViewModelFactory() })
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListMovieBinding.inflate(inflater, container, false)
        binding.rcMovieList.adapter = adapter
        binding.rcMovieList.layoutManager = GridLayoutManager(this.context, 3)
        model.movies.observe(viewLifecycleOwner) {
            when (it) {
                is SuccessResult -> {
                    binding.rcMovieList.adapter = MovieAdapter(it.data)
                }
                is ErrorResult -> {
                    throw Exception("server unavailable")
                }
                is PendingResult -> {
                    throw NotImplementedError("Pending result never sending ")
                }
            }
        }
        return binding.root
    }

    inner class MovieAdapter(private val movieList: List<Movie>) :
        RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

        inner class MovieHolder(item: View) : RecyclerView.ViewHolder(item) {
            private val binding = ListItemMovieBinding.bind(item)

            fun bind(movie: Movie) = with(binding) {
                model.downloadInto(movie.posterUrl, im)
                tvTitle.text = movie.name
                itemView.setOnClickListener {
                    val action =
                        MovieListFragmentDirections.actionListFragmentNavToDetailFragmentNav(movie.id)
                    it.findNavController().navigate(action)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
            return MovieHolder(view)
        }

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            holder.bind(movieList[position])
        }

        override fun getItemCount(): Int {
            return movieList.size
        }
    }
}
