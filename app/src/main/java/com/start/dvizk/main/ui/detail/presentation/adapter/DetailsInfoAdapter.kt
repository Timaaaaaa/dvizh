package com.start.dvizk.main.ui.detail.presentation.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.detail.data.model.DetailsInfoDataModel

class DetailsInfoAdapter(
	private val resources: Resources
) : RecyclerView.Adapter<DetailsInfoAdapter.ViewHolder>() {

	private var detailsInformation = mutableListOf<DetailsInfoDataModel>()

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

	fun setData(detailsInformation: MutableList<DetailsInfoDataModel>) {
		this.detailsInformation.clear()
		this.detailsInformation = detailsInformation
		notifyDataSetChanged()
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(detailsInfo: DetailsInfoDataModel) {
			val icon: ImageView = itemView.findViewById(R.id.item_details_info_icon)
			val header: TextView = itemView.findViewById(R.id.item_details_info_header)
			val title: TextView = itemView.findViewById(R.id.item_details_info_title)
			val subtitle: TextView = itemView.findViewById(R.id.item_details_info_subtitle)

			icon.setImageResource(detailsInfo.icon)
			header.text = detailsInfo.header
			title.text = detailsInfo.title
			subtitle.text = detailsInfo.subtitle

			if (subtitle.text == "") {
				subtitle.visibility = View.GONE
			} else {
				subtitle.visibility = View.VISIBLE
			}
		}
	}
}