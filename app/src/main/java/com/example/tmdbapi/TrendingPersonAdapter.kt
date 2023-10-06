package com.example.tmdbapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TrendingPersonAdapter(private val peopleList: MutableList<Person>) : RecyclerView.Adapter<TrendingPersonViewHolder>() {

    var onItemClick: ((Person) -> Unit)? = null

    fun appendPeople(people: List<Person>) {
        peopleList.addAll(people)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingPersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return TrendingPersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingPersonViewHolder, position: Int) {
        val person = peopleList[position]
        holder.bind(person) // Here, we bind the person data to the UI components in the ViewHolder
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(person)
        }
    }

    override fun getItemCount() = peopleList.size
}

class TrendingPersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
    //private val personName: TextView = itemView.findViewById(R.id.personName)
    //private val popularityValue: TextView = itemView.findViewById(R.id.popularityValue)
    //private val knownDepartment: TextView = itemView.findViewById(R.id.knownDepartment)

    fun bind(person: Person) {
        //personName.text = person.name
        //popularityValue.text = "Popularity: ${person.popularity}"
        //knownDepartment.text = "Known for: ${person.known_for_department}"

        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${person.profile_path}")
            .into(profileImage)
    }
}
