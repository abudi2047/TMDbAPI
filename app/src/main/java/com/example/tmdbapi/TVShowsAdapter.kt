package com.example.tmdbapi

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TVShowsAdapter : RecyclerView.Adapter<TVShowsAdapter.TVShowViewHolder>() {

    private val tvShows = mutableListOf<TVShows>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tvshow_item, parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow = tvShows[position]
    //      holder.tvTitle.text = tvShow.title
   //      holder.tvOverview.text = tvShow.overview
  //      holder.tvRating.text = "Rating: ${tvShow.rating}"
        // Load TV show poster image using an image loading library like Picasso or Glide
        // Example: Picasso.get().load(tvShow.posterPath).into(holder.ivPoster)
    }

    override fun getItemCount(): Int = tvShows.size

    fun appendTVShows(newTVShows: List<TVShows>) {
        tvShows.addAll(newTVShows)
        notifyDataSetChanged()
    }

    inner class TVShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      //  val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
      //  val tvOverview: TextView = itemView.findViewById(R.id.tvOverview) // Define tvOverview
      //  val tvRating: TextView = itemView.findViewById(R.id.tvRating) // Define tvRating
        // Initialize other view components here
    }
}

