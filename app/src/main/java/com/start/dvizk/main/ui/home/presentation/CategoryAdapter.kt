package com.start.dvizk.main.ui.home.presentation

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import okhttp3.internal.notify

class CategoryAdapter(private val resources: Resources): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

	private var categories = listOf<Category>()
	private var listener: OnCategoryItemClickListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = categories[position]
		holder.bind(item, listener)
	}

	override fun getItemCount(): Int {
		return categories.size
	}

	fun setData(categories: List<Category>) {
		this.categories = categories
		notifyDataSetChanged()
	}

	fun setListener(listener: OnCategoryItemClickListener) {
		this.listener = listener
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		private var image: ImageView = itemView.findViewById(R.id.item_category_icon)
		private var name: TextView = itemView.findViewById(R.id.item_category_title)


		fun bind(categorie: Category, listener: OnCategoryItemClickListener?) {
			name.text = categorie.name
			var imageUrl = ""
			if (categorie.name != "Все") {
				imageUrl = "http://161.35.145.58/"+categorie.image
			}
			Glide.with(itemView)
				.load(imageUrl)
				.error(R.drawable.ic_logo)
				.into(image)
			if (categorie.isSelected) {
				itemView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_purple_rounded_corners_fill, itemView.context.theme)
				name.setTextColor(resources.getColor(R.color.white))
			} else {
				itemView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_purple_rounded_corners, itemView.context.theme)
				name.setTextColor(resources.getColor(R.color.nav_focus_color))
			}
			itemView.setOnClickListener {
				categories.forEach{
					it.isSelected = false
					if(it.id == categorie.id) {
						it.isSelected = true
					}
				}
				notifyDataSetChanged()
				listener?.onItemClick(categorie)
			}
		}
	}
}