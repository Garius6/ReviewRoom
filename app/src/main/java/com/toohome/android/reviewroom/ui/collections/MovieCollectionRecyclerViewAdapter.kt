package com.toohome.android.reviewroom.ui.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.toohome.android.reviewroom.data.model.MovieCollection
import com.toohome.android.reviewroom.databinding.FragmentCollectionItemBinding

class MovieCollectionRecyclerViewAdapter(
    private val values: List<MovieCollection>
) : RecyclerView.Adapter<MovieCollectionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCollectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentCollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        fun bind(col: MovieCollection) {
            binding.itemNumber.text = col.id.toString()
            binding.content.text = col.movies.toString()
            itemView.setOnClickListener {
                val action =
                    MovieCollectionFragmentDirections.actionCollectionsFragmentToMovieCollectionDetailFragment(col.id)
                it.findNavController().navigate(action)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
