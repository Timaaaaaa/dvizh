package com.start.dvizk.main.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

class NotificationAdapter(private val list: List<com.start.dvizk.main.ui.notifications.Notification>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val notification = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_notification, parent, false)

		return ViewHolder(notification)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val notification = list[position]

		holder.icon.setImageResource(notification.icon)
		if (!notification.notViewed) {
			holder.notViewed.visibility = View.GONE
		} else {
			holder.notViewed.visibility = View.VISIBLE
		}
		holder.title.text = notification.title
		holder.subtitle.text = notification.subtitle
		holder.description.text = notification.description
	}

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val icon: ImageView = view.findViewById(R.id.item_notification_icon)
		val notViewed: ImageView = view.findViewById(R.id.item_notification_not_viewed)
		val title: TextView = view.findViewById(R.id.item_notification_title)
		val subtitle: TextView = view.findViewById(R.id.item_notification_subtitle)
		val description: TextView = view.findViewById(R.id.item_notification_description)
	}
}