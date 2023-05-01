package com.start.dvizk.main.ui.event.check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

class CheckAdapter(private val mList: List<CheckView>) : RecyclerView.Adapter<CheckAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckAdapter.ViewHolder {

		val checkView = LayoutInflater.from(parent.context)
			.inflate(R.layout.fragment_event_page_checklist, parent, false)

		return ViewHolder(checkView)
	}

	override fun getItemCount(): Int {
		return mList.size
	}

	override fun onBindViewHolder(holder: CheckAdapter.ViewHolder, position: Int) {

		val itemView = mList[position]

		holder.checkImage.setImageResource(itemView.checkImage)
		holder.checkText.text = itemView.checkText
	}

	inner class ViewHolder(checkView: View) : RecyclerView.ViewHolder(checkView) {
		val checkImage: ImageView = checkView.findViewById(R.id.check_image)
		val checkText: TextView = checkView.findViewById(R.id.check_text)
	}
}