package com.start.dvizk.main.ui.tickets.mytickets.presentation.adapter

import android.content.res.Resources
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
import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket
import com.start.dvizk.main.ui.tickets.ticket.presentation.OnTicketClickListener

class UpcomingTicketsAdapter(
	private val resources: Resources
) : RecyclerView.Adapter<UpcomingTicketsAdapter.ViewHolder>() {

	private var upcomingTickets = mutableListOf<MyTicket>()
	private var listener: OnTicketClickListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val upcomingTicket = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_my_upcoming_ticket, parent, false)

		return ViewHolder(upcomingTicket)
	}

	override fun getItemCount(): Int = upcomingTickets.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val upcomingTicket = upcomingTickets[position]
		holder.bind(upcomingTicket, listener)
	}

	fun setData(upcomingTickets: MutableList<MyTicket>) {
		this.upcomingTickets.clear()
		this.upcomingTickets = upcomingTickets
		notifyDataSetChanged()
	}

	fun setListener(listener: OnTicketClickListener) {
		this.listener = listener
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(myUpcomingTicket: MyTicket, listener: OnTicketClickListener?) {
			val image: ImageView = itemView.findViewById(R.id.item_my_upcoming_ticket_image)
			val title: TextView = itemView.findViewById(R.id.item_my_upcoming_ticket_title)
			val date: TextView = itemView.findViewById(R.id.item_my_upcoming_ticket_date)
			val location: TextView = itemView.findViewById(R.id.item_my_upcoming_ticket_location)
			val viewTicketButton: Button = itemView.findViewById(R.id.item_my_upcoming_ticket_view_ticket_button)
			val cancelButton: Button = itemView.findViewById(R.id.item_my_upcoming_ticket_cancel_ticket_button)

			Glide.with(itemView)
				.load(myUpcomingTicket.image)
				.centerCrop()
				.transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.my_ticket_image_radius)))
				.error(R.drawable.ic_logo)
				.into(image)

			title.text = myUpcomingTicket.name
			val fullDate = "${myUpcomingTicket.datetime.date} · ${myUpcomingTicket.datetime.startTime} - ${myUpcomingTicket.datetime.endTime}"
			date.text = fullDate
			location.text = myUpcomingTicket.address

			cancelButton.setOnClickListener{
				listener?.onCancelTicket(myUpcomingTicket)
			}

			viewTicketButton.setOnClickListener {
				listener?.onViewTicket(myUpcomingTicket)
			}
		}
	}
}