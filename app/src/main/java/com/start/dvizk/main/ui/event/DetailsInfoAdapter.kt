package com.start.dvizk.main.ui.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

class DetailsInfoAdapter(private val mList: List<DetailsInfo>) : RecyclerView.Adapter<DetailsInfoAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val detailsInfo = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_details_info, parent, false)

		return ViewHolder(detailsInfo)
	}

	override fun getItemCount(): Int {
		return mList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val detailsInfo = mList[position]

		holder.icon.setImageResource(detailsInfo.icon)
		holder.title.text = detailsInfo.title
		holder.subtitle1.text = detailsInfo.subtitle1
		holder.subtitle2.text = detailsInfo.subtitle2

		if (holder.subtitle2.text.isEmpty()) {
			holder.subtitle2.visibility = View.GONE
		}
	}

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val icon: ImageView = view.findViewById(R.id.icon)
		val title: TextView = view.findViewById(R.id.title)
		val subtitle1: TextView = view.findViewById(R.id.subtitle_1)
		val subtitle2: TextView = view.findViewById(R.id.subtitle_2)
	}
}