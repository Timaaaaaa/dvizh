package com.start.dvizk.main.ui.tickets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.tickets.model.MyFinishedTicket

class FinishedTicketsAdapter(private val finishedTickets: List<MyFinishedTicket>) : RecyclerView.Adapter<FinishedTicketsAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		
		val finishedTicket = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_my_finished_ticket, parent, false)
		
		return ViewHolder(finishedTicket)
	}

	override fun getItemCount(): Int {
		return finishedTickets.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		
		val finishedTicket = finishedTickets[position]
		
		holder.image.setImageResource(finishedTicket.image)
		holder.title.text = finishedTicket.title
		holder.date.text = finishedTicket.date
		holder.location.text = finishedTicket.location
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val image: ImageView = itemView.findViewById(R.id.item_my_finished_ticket_image)
		val title: TextView = itemView.findViewById(R.id.item_my_finished_ticket_title)
		val date: TextView = itemView.findViewById(R.id.item_my_finished_ticket_date)
		val location: TextView = itemView.findViewById(R.id.item_my_finished_ticket_location)
	}
}