package com.example.tmdbapi

import android.content.Intent
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
        providers.clear()
        providers.addAll(newProviders)
        notifyDataSetChanged()
    }

    fun appendProviders(providers: List<MovieProvider>) {
        this.providers.addAll(providers)
        notifyItemRangeInserted(this.providers.size - providers.size, providers.size)
    }

    class ProviderViewHolder(itemView: View, private val onProviderClick: (provider: MovieProvider) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val logo: ImageView = itemView.findViewById(R.id.item_provider_logo)
        private val name: TextView = itemView.findViewById(R.id.item_provider_name)

        fun bind(provider: MovieProvider) {
            itemView.setOnClickListener {
                // Passing each individual attribute as opposed to the whole object
                val context = itemView.context
                val detailIntent = Intent(context, ProviderDetailActivity::class.java)

                detailIntent.putExtra(ProviderDetailActivity.EXTRA_PROVIDER_ID, provider.id)
                detailIntent.putExtra(ProviderDetailActivity.EXTRA_PROVIDER_NAME, provider.name)
                detailIntent.putExtra(ProviderDetailActivity.EXTRA_PROVIDER_LOGO, provider.logoPath)
                detailIntent.putExtra(ProviderDetailActivity.EXTRA_PROVIDER_DISPLAY_PRIORITY, provider.displayPriority)

                context.startActivity(detailIntent)
            }
            val logoUrl = provider.logoPath

            Glide.with(itemView)
                .load(logoUrl)
                .transform(CenterCrop())
                .into(logo)

            name.text = provider.name
        }
    }
}
