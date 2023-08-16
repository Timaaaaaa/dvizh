package com.start.dvizk.main.ui.tickets.mytickets.presentation.adapter

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
import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket

class CanceledTicketsAdapter(
	private val resources: Resources
) : RecyclerView.Adapter<CanceledTicketsAdapter.ViewHolder>() {

	private var canceledTickets = mutableListOf<MyTicket>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val canceledTicket = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_my_canceled_ticket, parent, false)

		return ViewHolder(canceledTicket)
	}

	override fun getItemCount(): Int = canceledTickets.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val canceledTicket = canceledTickets[position]
		holder.bind(canceledTicket)
	}

	fun setData(canceledTickets: MutableList<MyTicket>) {
		this.canceledTickets.clear()
		this.canceledTickets = canceledTickets
		notifyDataSetChanged()
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

		val image: ImageView = itemView.findViewById(R.id.item_my_canceled_ticket_image)
		val title: TextView = itemView.findViewById(R.id.item_my_canceled_ticket_title)
		val date: TextView = itemView.findViewById(R.id.item_my_canceled_ticket_date)
		val location: TextView = itemView.findViewById(R.id.item_my_canceled_ticket_location)

		fun bind(myCanceledTicket: MyTicket) {

			Glide.with(itemView)
				.load(image)
				.centerCrop()
				.transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.my_ticket_image_radius)))
				.error(R.drawable.ic_logo)
				.into(image)

			title.text = myCanceledTicket.name
			val fullDate = "${myCanceledTicket.datetime.date} · ${myCanceledTicket.datetime.startTime} - ${myCanceledTicket.datetime.endTime}"
			date.text = fullDate
			location.text = myCanceledTicket.address
		}
	}
}