package com.start.dvizk.main.ui.tickets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.tickets.model.MyCanceledTicket

class CanceledTicketsAdapter(private val myCanceledTickets: List<MyCanceledTicket>) : RecyclerView.Adapter<CanceledTicketsAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val canceledTicket = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_my_canceled_ticket, parent, false)

		return ViewHolder(canceledTicket)
	}

	override fun getItemCount(): Int {
		return myCanceledTickets.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val canceledTicket = myCanceledTickets[position]

		holder.image.setImageResource(canceledTicket.image)
		holder.title.text = canceledTicket.title
		holder.date.text = canceledTicket.date
		holder.location.text = canceledTicket.location
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
		val image: ImageView = itemView.findViewById(R.id.item_my_canceled_ticket_image)
		val title: TextView = itemView.findViewById(R.id.item_my_canceled_ticket_title)
		val date: TextView = itemView.findViewById(R.id.item_my_canceled_ticket_date)
		val location: TextView = itemView.findViewById(R.id.item_my_canceled_ticket_location)
	}
}