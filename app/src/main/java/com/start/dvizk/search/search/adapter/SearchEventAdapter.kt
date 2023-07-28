package com.start.dvizk.search.search.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Event

class SearchEventAdapter(private val mList: List<Event>, val resources: Resources) : RecyclerView.Adapter<SearchEventAdapter.ViewHolder>() {

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

		holder.title.text = favoriteEvent.name
//		holder.date.text = favoriteEvent.date
//		holder.location.text = favoriteEvent.location

		Glide.with(holder.image)
			.load(favoriteEvent.main_image)
			.transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.big_event_default_image_radius)))
			.into(holder.image)

		if (favoriteEvent.is_favorite) {
			holder.isFavorite.setImageResource(R.drawable.ic_like_accent_filled)
		} else {
			holder.isFavorite.setImageResource(R.drawable.ic_like_accent)
		}

		holder.isFavorite.setOnClickListener {
			favoriteEvent.is_favorite = !favoriteEvent.is_favorite
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