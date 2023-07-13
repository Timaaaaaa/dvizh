package com.start.dvizk.main.ui.detail.presentation

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.detail.data.model.DetailsInfoDataModel

class DetailsInfoAdapter(
	private val resources: Resources
) : RecyclerView.Adapter<DetailsInfoAdapter.ViewHolder>() {

	private var detailsInformation = listOf<DetailsInfoDataModel>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val detailsInfo = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_details_info, parent, false)

		return ViewHolder(detailsInfo)
	}

	override fun getItemCount(): Int {
		return detailsInformation.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val detailsInfo = detailsInformation[position]
		holder.bind(detailsInfo)
	}

	fun setData(detailsInformation: List<DetailsInfoDataModel>) {
		this.detailsInformation = detailsInformation
		notifyDataSetChanged()
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(detailsInfo: DetailsInfoDataModel) {
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