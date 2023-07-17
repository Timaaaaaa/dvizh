package com.start.dvizk.search.search.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.search.search.presentation.SearchCategoryItemClick

class SearchCategoryAdapter(private val resources: Resources): RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder>() {

	private var categories = listOf<Category>()
	private var listener: SearchCategoryItemClick? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_search, parent, false)
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

		private var image: ImageView = itemView.findViewById(R.id.item_category_icon)
		private var name: TextView = itemView.findViewById(R.id.item_category_title)
		private var border: View = itemView.findViewById(R.id.icon_card_border)


		fun bind(categorie: Category, listener: SearchCategoryItemClick?, position: Int) {
			name.text = categorie.name
			var imageUrl = ""
			if (categorie.name != "Все") {
				imageUrl = categorie.image
			}
			Glide.with(itemView)
				.load(imageUrl)
				.error(R.drawable.ic_logo)
				.into(image)
			if (categorie.isSelected) {
				border.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_search_category_border_selected, itemView.context.theme)
				name.setTextColor(resources.getColor(R.color.white))
				border.setPadding(4,4,4,4)
			} else {
				border.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_search_category_border_unselect, itemView.context.theme)
				name.setTextColor(resources.getColor(R.color.grey_default))
				border.setPadding(1,1,1,1)
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