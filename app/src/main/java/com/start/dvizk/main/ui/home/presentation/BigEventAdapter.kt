package com.start.dvizk.main.ui.home.presentation

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Event
import okhttp3.internal.notify

class BigEventAdapter(
	private val resources: Resources
): RecyclerView.Adapter<BigEventAdapter.ViewHolder>() {

	private var events = listOf<Event>()
	private var listener: OnItemClickListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
		return ViewHolder(view, resources)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = events[position]
		holder.bind(item, listener)
	}

	override fun getItemCount(): Int {
		return events.size
	}

	fun setData(events: List<Event>) {
		this.events = events
		notifyDataSetChanged()
	}

	class ViewHolder(itemView: View, val resources: Resources) : RecyclerView.ViewHolder(itemView) {

		private var image: ImageView = itemView.findViewById(R.id.item_event_image)
		private var title: TextView = itemView.findViewById(R.id.item_event_title_text_view)
		private var data: TextView = itemView.findViewById(R.id.item_event_subtitle_text_view)
		private var address: TextView = itemView.findViewById(R.id.item_event_address_text_view)

		fun bind(event: Event, listener: OnItemClickListener?) {
			title.text = event.name
			Glide.with(itemView)
				.load(event.main_image)
				.transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.big_event_image_radius)))
				.into(image)

			itemView.setOnClickListener {
				listener?.onItemClick(event)
			}
		}
	}
}