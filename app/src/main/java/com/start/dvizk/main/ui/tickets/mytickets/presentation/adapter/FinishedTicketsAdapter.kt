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

class FinishedTicketsAdapter(
	private val resources: Resources
) : RecyclerView.Adapter<FinishedTicketsAdapter.ViewHolder>() {

	private var finishedTickets = mutableListOf<MyTicket>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		
		val finishedTicket = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_my_finished_ticket, parent, false)
		
		return ViewHolder(finishedTicket)
	}

	override fun getItemCount(): Int = finishedTickets.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val finishedTicket = finishedTickets[position]
		holder.bind(finishedTicket)
	}

	fun setData(finishedTickets: MutableList<MyTicket>) {
		this.finishedTickets.clear()
		this.finishedTickets = finishedTickets
		notifyDataSetChanged()
	}

	fun viewTicket() {

	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val image: ImageView = itemView.findViewById(R.id.item_my_finished_ticket_image)
		val title: TextView = itemView.findViewById(R.id.item_my_finished_ticket_title)
		val date: TextView = itemView.findViewById(R.id.item_my_finished_ticket_date)
		val location: TextView = itemView.findViewById(R.id.item_my_finished_ticket_location)

		fun bind(myFinishedTicket: MyTicket) {
			Glide.with(itemView)
				.load(image)
				.centerCrop()
				.transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.my_ticket_image_radius)))
				.error(R.drawable.ic_logo)
				.into(image)

			title.text = myFinishedTicket.name
			val fullDate = "${myFinishedTicket.datetime.date} · ${myFinishedTicket.datetime.startTime} - ${myFinishedTicket.datetime.endTime}"
			date.text = fullDate
			location.text = myFinishedTicket.address
		}
	}
}