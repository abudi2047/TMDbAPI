package com.example.tmdbapi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class TVShowsAdapter(
    private var tvShows: MutableList<TVShows>,
    private val onTVShowClick: (TVShows) -> Unit
) : RecyclerView.Adapter<TVShowsAdapter.TVShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return TVShowViewHolder(view, onTVShowClick)
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bind(tvShows[position])
    }

    fun appendTVShows(tvShows: List<TVShows>) {
        val positionStart = this.tvShows.size
        this.tvShows.addAll(tvShows)
        notifyItemRangeInserted(positionStart, tvShows.size)
    }

    class TVShowViewHolder(itemView: View, private val onTVShowClick: (TVShows) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(tvShow: TVShows) {
            val imageUrl = "https://image.tmdb.org/t/p/w342${tvShow.posterPath}"
            Log.d("TVShowsAdapter", "Loading image from: $imageUrl")

            Glide.with(itemView)
                .load(imageUrl)
                .transform(CenterCrop())
                .into(poster)

            itemView.setOnClickListener { onTVShowClick(tvShow) }
        }
    }
}
