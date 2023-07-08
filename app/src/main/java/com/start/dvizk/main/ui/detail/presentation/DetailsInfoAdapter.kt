package com.start.dvizk.main.ui.detail.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.detail.data.DetailsInfoDataModal

class DetailsInfoAdapter(private val detailsInfoTickets: MutableList<DetailsInfoDataModal>) : RecyclerView.Adapter<DetailsInfoAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val detailsInfo = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_details_info, parent, false)

		return ViewHolder(detailsInfo)
	}

	override fun getItemCount(): Int {
		return detailsInfoTickets.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val detailsInfo = detailsInfoTickets[position]
		holder.bind(detailsInfo, position)
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(detailsInfo: DetailsInfoDataModal, position: Int) {
			val location: TextView = itemView.findViewById(R.id.item_details_info_location)
			val date: TextView = itemView.findViewById(R.id.item_details_info_date)
			val time: TextView = itemView.findViewById(R.id.item_details_info_time)
			val language: TextView = itemView.findViewById(R.id.item_details_info_language)
			val price: TextView = itemView.findViewById(R.id.item_details_info_price)

			location.text = detailsInfo.location
			date.text = detailsInfo.date
			time.text = detailsInfo.time
			language.text = detailsInfo.language
			price.text = detailsInfo.price
		}
	}
}