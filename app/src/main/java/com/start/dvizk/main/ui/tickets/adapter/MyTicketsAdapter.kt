package com.start.dvizk.main.ui.tickets.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.start.dvizk.R
import com.start.dvizk.main.ui.tickets.MyTicket

class MyTicketsAdapter(private val myTickets: List<MyTicket>) : RecyclerView.Adapter<MyTicketsAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val myTicket = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_my_ticket, parent, false)

		return ViewHolder(myTicket)
	}

	override fun getItemCount(): Int {
		return myTickets.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val myTicket = myTickets[position]

//		Glide.with(holder.image)
//			.load(myTicket.image)
//			.centerCrop()
//			.transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.my_ticket_image_radius)))
//			.into(holder.image)

		holder.image.setImageResource(myTicket.image)
		holder.title.text = myTicket.title
		holder.date.text = myTicket.date
		holder.location.text = myTicket.location
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val image: ImageView = itemView.findViewById(R.id.item_my_ticket_image)
		var title: TextView = itemView.findViewById(R.id.item_my_ticket_title)
		val date: TextView = itemView.findViewById(R.id.item_my_ticket_date)
		val location: TextView = itemView.findViewById(R.id.item_my_ticket_location)
	}
}