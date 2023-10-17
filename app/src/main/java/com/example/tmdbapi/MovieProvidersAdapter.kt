package com.example.tmdbapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class MovieProvidersAdapter(
    private var providers: MutableList<MovieProvider>,
    private val onProviderClick: (provider: MovieProvider) -> Unit
) : RecyclerView.Adapter<MovieProvidersAdapter.ProviderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_provider, parent, false)
        return ProviderViewHolder(view, onProviderClick)
    }

    override fun getItemCount(): Int = providers.size

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        holder.bind(providers[position])
    }

    fun updateData(newProviders: List<MovieProvider>) {
        providers.clear() // Clear the old data
        providers.addAll(newProviders) // Add the new providers
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    fun appendProviders(providers: List<MovieProvider>) {
        this.providers.addAll(providers)
        notifyItemRangeInserted(this.providers.size - providers.size, providers.size) // Optimized for batch updates
    }

    class ProviderViewHolder(itemView: View, private val onProviderClick: (provider: MovieProvider) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val logo: ImageView = itemView.findViewById(R.id.item_provider_logo)
        private val name: TextView = itemView.findViewById(R.id.item_provider_name)

        fun bind(provider: MovieProvider) {
            itemView.setOnClickListener { onProviderClick(provider) }

            // Assuming that the logo URL is complete (including "http(s)://")
            val logoUrl = provider.logoPath

            Glide.with(itemView)
                .load(logoUrl)
                .transform(CenterCrop())
                .into(logo)

            name.text = provider.name
        }
    }
}
