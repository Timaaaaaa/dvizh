package com.start.dvizk.main.ui.home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import okhttp3.internal.notify

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

	private var categories = listOf<Category>()
	private var listener: OnItemClickListener? = null

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

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		private var image: ImageView = itemView.findViewById(R.id.item_category_icon)
		private var name: TextView = itemView.findViewById(R.id.item_category_title)

		fun bind(categorie: Category, listener: OnItemClickListener?) {
			name.text = categorie.name
			Glide.with(itemView)
				.load("http://161.35.145.58/"+categorie.image)
				.into(image)

//			itemView.setOnClickListener {
//				listener?.onItemClick(event)
//			}
		}
	}
}