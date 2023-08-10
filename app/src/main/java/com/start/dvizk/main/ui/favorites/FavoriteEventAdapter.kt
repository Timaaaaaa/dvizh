package com.start.dvizk.main.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

class FavoriteEventAdapter(private val mList: List<FavoriteEvent>) : RecyclerView.Adapter<FavoriteEventAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val favoriteEvent = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_favorite_event, parent, false)

		return ViewHolder(favoriteEvent)
	}

	override fun getItemCount(): Int {
		return mList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val favoriteEvent = mList[position]

		holder.image.setImageResource(favoriteEvent.image)
		holder.title.text = favoriteEvent.title
		holder.date.text = favoriteEvent.date
		holder.location.text = favoriteEvent.location

		if (favoriteEvent.isFavorite) {
			holder.isFavorite.setImageResource(R.drawable.ic_like_accent_filled)
		} else {
			holder.isFavorite.setImageResource(R.drawable.ic_like_accent)
		}

		holder.isFavorite.setOnClickListener {
			favoriteEvent.isFavorite = !favoriteEvent.isFavorite
			notifyDataSetChanged()
		}
	}

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val image: ImageView = view.findViewById(R.id.item_favorite_event_image)
		val title: TextView = view.findViewById(R.id.item_favorite_event_title)
		val date: TextView = view.findViewById(R.id.item_favorite_event_date)
		val location: TextView = view.findViewById(R.id.item_favorite_event_location)
		val isFavorite: ImageView = view.findViewById(R.id.item_favorite_event_is_favorite_icon)
	}
}