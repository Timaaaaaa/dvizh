package com.start.dvizk.main.ui.tickets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.start.dvizk.R
import com.start.dvizk.main.ui.tickets.model.MyUpcomingTicket

class UpcomingTicketsAdapter(private var upcomingTickets: MutableList<MyUpcomingTicket>) : RecyclerView.Adapter<UpcomingTicketsAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val upcomingTicket = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_my_upcoming_ticket, parent, false)

		return ViewHolder(upcomingTicket)
	}

	override fun getItemCount(): Int {
		return upcomingTickets.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val upcomingTicket = upcomingTickets[position]
		holder.bind(upcomingTicket, position)
	}

	fun cancelTicket(position: Int) {
		upcomingTickets.removeAt(position)
		notifyDataSetChanged()
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(myUpcomingTicket: MyUpcomingTicket, position: Int) {
			val image: ImageView = itemView.findViewById(R.id.item_my_upcoming_ticket_image)
			val title: TextView = itemView.findViewById(R.id.item_my_upcoming_ticket_title)
			val date: TextView = itemView.findViewById(R.id.item_my_upcoming_ticket_date)
			val location: TextView = itemView.findViewById(R.id.item_my_upcoming_ticket_location)
			val cancelButton: Button = itemView.findViewById(R.id.item_my_upcoming_ticket_cancel_ticket_button)

			Glide.with(itemView)
				.load(myUpcomingTicket.image)
				.centerCrop()
//				.transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.my_ticket_image_radius)))
				.into(image)

			title.text = myUpcomingTicket.title
			date.text = myUpcomingTicket.date
			location.text = myUpcomingTicket.location

			cancelButton.setOnClickListener{
				cancelTicket(position)
			}
		}
	}
}