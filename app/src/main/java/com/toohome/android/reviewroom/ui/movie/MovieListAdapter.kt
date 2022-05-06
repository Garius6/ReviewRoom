package com.toohome.android.reviewroom.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.toohome.android.reviewroom.NavGraphDirections
import com.toohome.android.reviewroom.R
import com.toohome.android.reviewroom.data.model.Movie
import com.toohome.android.reviewroom.databinding.ListItemMovieBinding
import com.toohome.android.reviewroom.ui.MovieBasicViewModel

class MovieListAdapter(private val movieList: List<Movie>, private val model: MovieBasicViewModel) :
    RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    inner class MovieHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ListItemMovieBinding.bind(item)
        fun bind(movie: Movie) = with(binding) {
            model.loadPosterInto(movie, im)
            tvTitle.text = movie.name
            itemView.setOnClickListener {
                val action =
                    NavGraphDirections.actionGlobalMovieDetailFragment(movie.id)
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
