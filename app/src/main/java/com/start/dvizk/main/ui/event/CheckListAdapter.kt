package com.start.dvizk.main.ui.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

class CheckListAdapter(private val mList: List<CheckList>) : RecyclerView.Adapter<CheckListAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val checkView = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_checklist, parent, false)

		return ViewHolder(checkView)
	}

	override fun getItemCount(): Int {
		return mList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val checkView = mList[position]

		holder.checkImage.setImageResource(checkView.checkImage)
		holder.checkText.text = checkView.checkText
	}

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val checkImage: ImageView = view.findViewById(R.id.check_image)
		val checkText: TextView = view.findViewById(R.id.check_text)
	}
}