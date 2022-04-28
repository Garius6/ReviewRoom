package com.toohome.android.reviewroom.ui.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toohome.android.reviewroom.data.model.MovieCollection
import com.toohome.android.reviewroom.databinding.FragmentMovieCollectionItemBinding
import com.toohome.android.reviewroom.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 */
class MovieCollectionRecyclerViewAdapter(
    private val values: List<MovieCollection>
) : RecyclerView.Adapter<MovieCollectionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMovieCollectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()
        holder.contentView.text = item.movies.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMovieCollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
