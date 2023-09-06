package com.start.dvizk.create.steps.bottomsheet.universal

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem

class SelectListAdapter(
	private val resources: Resources,
	private val isMultiSelect: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var items = listOf<SelectItem>()
	private var listener: OnSelectListClickListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_subcategory_check, parent, false)
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

	fun setListener(listener: OnSelectListClickListener?) {
		this.listener = listener
	}

	fun setData(items: List<SelectItem>) {
		this.items = items
		notifyDataSetChanged()
	}

	inner class ViewHolder1(itemView: View, val resources: Resources) :
		RecyclerView.ViewHolder(itemView) {

		private var title: TextView = itemView.findViewById(R.id.check_text)
		private var checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

		fun bind(item: SelectItem) {
			checkBox.isChecked = item.isSelect
			title.text = item.name
			itemView.setOnClickListener {
				checkBox.performClick()
				if (isMultiSelect) {
					listener?.onMultiItemsSelect(item.id)

					return@setOnClickListener
				}
				listener?.onItemSelect(item.id)
			}
		}
	}
}