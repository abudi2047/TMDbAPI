package com.example.tmdbapi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieProvidersAdapter(
    providers: List<MovieProvider>, // Changed to List
    private val onItemClicked: (MovieProvider) -> Unit
) : RecyclerView.Adapter<MovieProvidersAdapter.ViewHolder>() {

    private var data: MutableList<MovieProvider> = providers.toMutableList() // Using a private variable 'data' for the actual data manipulation

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.providerLogo)

        init {
            itemView.setOnClickListener {
                val movieProvider = data[adapterPosition]
                onItemClicked(movieProvider)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_provider, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = data[position]
        Glide.with(holder.logo.context)
            .load("https://image.tmdb.org/t/p/w500${provider.logoPath}")
            .into(holder.logo)
    }

    fun updateData(newData: List<MovieProvider>) {
        Log.d("DEBUG_TAG", "Adapter data updating: $newData")
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}
