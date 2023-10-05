package com.example.tmdbapi

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class TrendingPersonDetailActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var personName: TextView
    private lateinit var popularityValue: TextView
    private lateinit var knownDepartment: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_person_detail)

        // Initializing views with findViewById
        profileImage = findViewById(R.id.profileImage)
        personName = findViewById(R.id.personName)
        popularityValue = findViewById(R.id.popularityValue)
        knownDepartment = findViewById(R.id.knownDepartment)

        // Get the Person object passed from the previous activity
        val person: Person = intent.getParcelableExtra("selected_person")!!

        // Load the data into your views
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/${person.profile_path}").into(profileImage)
        personName.text = person.name
        popularityValue.text = person.popularity.toInt().toString()
        knownDepartment.text = person.known_for_department
    }
}
