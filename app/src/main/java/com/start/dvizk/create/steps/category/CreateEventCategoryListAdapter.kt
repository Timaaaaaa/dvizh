package com.start.dvizk.create.steps.category

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.create.steps.bottomsheet.OnCategoryCheckItemClickListener
import com.start.dvizk.create.steps.bottomsheet.universal.OnSelectListClickListener
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem
import com.start.dvizk.main.ui.home.presentation.model.Category

class CreateEventCategoryListAdapter(
	private val resources: Resources
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var items = listOf<Category>()
	private var listener: OnCategoryListDeleteItem? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_create_event_category, parent, false)
		return ViewHolder1(view, resources)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val item = items[position]
		when (holder) {
			is ViewHolder1 -> {
				holder.bind(item)
			}
			else -> throw IllegalArgumentException("Invalid view holder type")
		}
	}

	override fun getItemCount(): Int {
		return items.size
	}

	fun setListener(listener: OnCategoryListDeleteItem) {
		this.listener = listener
	}

	fun setData(items: List<Category>) {
		this.items = items
		notifyDataSetChanged()
	}

	inner class ViewHolder1(itemView: View, val resources: Resources) :
		RecyclerView.ViewHolder(itemView) {

		private var title: TextView = itemView.findViewById(R.id.check_text)
		private var deleteImage: ImageView = itemView.findViewById(R.id.delete)

		fun bind(item: Category) {
			title.text = item.name
			deleteImage.setOnClickListener {
				listener?.onDelete(item)
			}
		}
	}
}