package com.start.dvizk.main.ui.detail.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.detail.data.CheckListDataModal

class CheckListAdapter(private val checkList: MutableList<CheckListDataModal>) : RecyclerView.Adapter<CheckListAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val checkView = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_checklist, parent, false)

		return ViewHolder(checkView)
	}

	override fun getItemCount(): Int {
		return checkList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val checkListItem = checkList[position]
		holder.bind(checkListItem, position)
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(checkListItem: CheckListDataModal, position: Int) {
			val checkImage: ImageView = itemView.findViewById(R.id.check_image)
			val checkText: TextView = itemView.findViewById(R.id.check_text)

			checkImage.setImageResource(checkListItem.checkImage)
			checkText.text = checkListItem.checkText
		}
	}
}