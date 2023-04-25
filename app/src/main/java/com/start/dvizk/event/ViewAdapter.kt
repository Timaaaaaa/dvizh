package com.start.dvizk.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

class ViewAdapter(private val mList: List<ItemView>) : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val itemView = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_view, parent, false)

		return ViewHolder(itemView)
	}

	override fun getItemCount(): Int {
		return mList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val itemView = mList[position]

		holder.icon.setImageResource(itemView.icon)
		holder.title.text = itemView.title
		holder.subtitle.text = itemView.subtitle
		holder.button.setCompoundDrawablesWithIntrinsicBounds(itemView.buttonImage, 0, 0, 0);
		holder.button.text = itemView.buttonText

		if (holder.button.text.isEmpty()) {
			holder.button.visibility = View.GONE
		}
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val icon: ImageView = itemView.findViewById(R.id.icon)
		val title: TextView = itemView.findViewById(R.id.title)
		val subtitle: TextView = itemView.findViewById(R.id.subtitle)
		val button: Button = itemView.findViewById(R.id.btn)
	}
}