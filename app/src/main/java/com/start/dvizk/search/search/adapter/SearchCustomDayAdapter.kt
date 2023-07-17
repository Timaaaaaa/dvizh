package com.start.dvizk.search.search.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.search.search.presentation.SearchCategoryItemClick

class SearchCustomDayAdapter(private val resources: Resources): RecyclerView.Adapter<SearchCustomDayAdapter.ViewHolder>() {

	private var categories = listOf<Category>()
	private var listener: SearchCategoryItemClick? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_custom_month, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = categories[position]
		holder.bind(item, listener, position)
	}

	override fun getItemCount(): Int {
		return categories.size
	}

	fun setData(categories: List<Category>) {
		this.categories = categories
		notifyDataSetChanged()
	}

	fun setListener(listener: SearchCategoryItemClick) {
		this.listener = listener
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		private var name: TextView = itemView.findViewById(R.id.item_custom_month_title)
		private var subtitle: TextView = itemView.findViewById(R.id.item_custom_year)
		private var mainView: View = itemView.findViewById(R.id.item_custom_day_view)


		fun bind(categorie: Category, listener: SearchCategoryItemClick?, position: Int) {
			name.text = categorie.name

			if (categorie.isSelected) {
				mainView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_search_category_border_selected, itemView.context.theme)
				name.setTextColor(resources.getColor(R.color.white))
				subtitle.setTextColor(resources.getColor(R.color.white))
			} else {
				mainView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_search_category_border_unselect, itemView.context.theme)
				name.setTextColor(resources.getColor(R.color.grey_default))
				subtitle.setTextColor(resources.getColor(R.color.grey_default))
			}

			itemView.setOnClickListener {
				categories.forEach{
					if(it.id == categorie.id) {
						it.isSelected = !it.isSelected
					}

					notifyItemChanged(position)
				}
				listener?.onItemClick(categorie)
			}
		}
	}
}