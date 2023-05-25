package com.start.dvizk.main.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Category

class CategoryAdapter(private val list: List<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val category = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_category, parent, false)

		return ViewHolder(category)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val category = list[position]

		holder.name.text = category.name

		val itemView = holder.icon.rootView

		if (category.isSelected) {
			holder.itemView.background = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.bg_purple_rounded_corners_fill, itemView.context.theme)
			holder.name.setTextColor(holder.itemView.resources.getColor(R.color.white))
		} else {
			holder.itemView.background = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.bg_purple_rounded_corners, itemView.context.theme)
			holder.name.setTextColor(holder.itemView.resources.getColor(R.color.nav_focus_color))
		}

		itemView.setOnClickListener {
			list.forEach{
				it.isSelected = false
				if(it.name == category.name) {
					it.isSelected = true
				}
			}
			notifyDataSetChanged()
		}
	}

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val icon: ImageView = view.findViewById(R.id.item_category_icon)
		val name: TextView = view.findViewById(R.id.item_category_title)
	}
}