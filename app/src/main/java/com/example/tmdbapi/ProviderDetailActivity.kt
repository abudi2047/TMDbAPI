package com.example.tmdbapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ProviderDetailActivity : AppCompatActivity() {

    private lateinit var logo: ImageView
    private lateinit var name: TextView
    private lateinit var displayPriority: TextView

    companion object {
        private const val EXTRA_PROVIDER = "extra_provider"

        fun newIntent(context: Context, provider: MovieProvider): Intent {
            val intent = Intent(context, ProviderDetailActivity::class.java)
            intent.putExtra(EXTRA_PROVIDER, provider)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_detail)

        logo = findViewById(R.id.provider_logo)
        name = findViewById(R.id.provider_name)
        displayPriority = findViewById(R.id.provider_display_priority)

        val provider = intent.getParcelableExtra<MovieProvider>(EXTRA_PROVIDER)
        if (provider != null) {
            populateDetails(provider)
        } else {
            finish()
        }
    }

    private fun populateDetails(provider: MovieProvider) {
        Glide.with(this)
            .load(provider.logoPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(logo)

        name.text = provider.name
        displayPriority.text = getString(R.string.display_priority, provider.displayPriority)
    }
}
