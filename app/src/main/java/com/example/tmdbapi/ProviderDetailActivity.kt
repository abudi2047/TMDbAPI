package com.example.tmdbapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdbapi.databinding.ActivityProviderDetailBinding

class ProviderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProviderDetailBinding


    companion object {
        const val EXTRA_PROVIDER_ID = "extra_provider_id"
        const val EXTRA_PROVIDER_NAME = "extra_provider_name"
        const val EXTRA_PROVIDER_LOGO = "extra_provider_logo"
        const val EXTRA_PROVIDER_DISPLAY_PRIORITY = "extra_provider_display_priority"

  // Removed this function as we are no longer using passing the entire MovieProvider object

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val providerId = intent.getIntExtra(EXTRA_PROVIDER_ID, -1)
        val providerName = intent.getStringExtra(EXTRA_PROVIDER_NAME)
        val providerLogo = intent.getStringExtra(EXTRA_PROVIDER_LOGO)
        val providerDisplayPriority = intent.getIntExtra(EXTRA_PROVIDER_DISPLAY_PRIORITY, 0)

        if (providerId != -1 && providerName != null && providerLogo != null) {
            populateDetails(providerId, providerName, providerLogo, providerDisplayPriority)
        } else {
            finish()
        }
    }

    private fun populateDetails(id: Int, name: String, logoPath: String, displayPriority: Int) {
        Glide.with(this)
            .load(logoPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.providerLogo)

        binding.providerName.text = name
        binding.providerDisplayPriority.text = getString(R.string.display_priority, displayPriority)
    }
}
